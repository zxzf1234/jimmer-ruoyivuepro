package cn.iocoder.yudao.service.service.infra.social;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.iocoder.yudao.framework.common.util.http.HttpUtils;
import cn.iocoder.yudao.framework.social.core.YudaoAuthRequestFactory;
import cn.iocoder.yudao.service.api.system.social.dto.SocialUserBindReqDTO;
import cn.iocoder.yudao.service.enums.system.social.SocialTypeEnum;
import cn.iocoder.yudao.service.model.infra.social.SystemSocialUser;
import cn.iocoder.yudao.service.model.infra.social.SystemSocialUserBind;
import cn.iocoder.yudao.service.model.infra.social.SystemSocialUserBindDraft;
import cn.iocoder.yudao.service.model.infra.social.SystemSocialUserDraft;
import cn.iocoder.yudao.service.repository.infra.social.SystemSocialUserBindRepository;
import cn.iocoder.yudao.service.repository.infra.social.SystemSocialUserRepository;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.framework.common.util.json.JsonUtils.toJsonString;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.*;

/**
 * 社交用户 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class SocialUserServiceImpl implements SocialUserService {

    @Resource// 由于自定义了 YudaoAuthRequestFactory 无法覆盖默认的 AuthRequestFactory，所以只能注入它
    private YudaoAuthRequestFactory yudaoAuthRequestFactory;

    @Resource
    private SystemSocialUserBindRepository systemSocialUserBindRepository;
    @Resource
    private SystemSocialUserRepository systemSocialUserRepository;

    @Override
    public String getAuthorizeUrl(Integer type, String redirectUri) {
        // 获得对应的 AuthRequest 实现
        AuthRequest authRequest = yudaoAuthRequestFactory.get(SocialTypeEnum.valueOfType(type).getSource());
        // 生成跳转地址
        String authorizeUri = authRequest.authorize(AuthStateUtils.createState());
        return HttpUtils.replaceUrlQuery(authorizeUri, "redirect_uri", redirectUri);
    }

    @Override
    public SystemSocialUser authSocialUser(Integer type, String code, String state) {
        // 优先从 DB 中获取，因为 code 有且可以使用一次。
        // 在社交登录时，当未绑定 User 时，需要绑定登录，此时需要 code 使用两次
        Optional<SystemSocialUser> opSocialUser = systemSocialUserRepository.findByTypeAndCodeAndState(type, code, state);
        if (opSocialUser.isPresent()) {
            return opSocialUser.get();
        }

        // 请求获取
        AuthUser authUser = getAuthUser(type, code, state);
        Assert.notNull(authUser, "三方用户不能为空");

        // 保存到 DB 中
        SystemSocialUser socialUser;
        opSocialUser = systemSocialUserRepository.findByTypeAndOpenid(type, authUser.getUuid());
        if (opSocialUser.isPresent()) {
            socialUser = opSocialUser.get();
        }
        socialUser = SystemSocialUserDraft.$.produce(SystemSocialUser->{
            // 需要保存 code + state 字段，保证后续可查询
            SystemSocialUser.setType(type).setCode(code).setState(state)
                    .setOpenid(authUser.getUuid()).setToken(authUser.getToken().getAccessToken()).setRawTokenInfo((toJsonString(authUser.getToken())))
                    .setNickname(authUser.getNickname()).setAvatar(authUser.getAvatar()).setRawUserInfo(toJsonString(authUser.getRawUserInfo()));
        });
        if (!opSocialUser.isPresent()) {
            systemSocialUserRepository.insert(socialUser);
        } else {
            systemSocialUserRepository.update(socialUser);
        }
        return socialUser;
    }

    @Override
    public List<SystemSocialUser> getSocialUserList(Long userId, Integer userType) {
        // 获得绑定
        List<SystemSocialUserBind> socialUserBinds = systemSocialUserBindRepository.findByUserIdAndUserType(userId, userType);
        if (CollUtil.isEmpty(socialUserBinds)) {
            return Collections.emptyList();
        }
        // 获得社交用户
        return systemSocialUserRepository.findByIds(convertSet(socialUserBinds, SystemSocialUserBind::socialUserId));
    }

    @Override
    @Transactional
    public void bindSocialUser(SocialUserBindReqDTO reqDTO) {
        // 获得社交用户
        SystemSocialUser socialUser = authSocialUser(reqDTO.getType(), reqDTO.getCode(), reqDTO.getState());
        Assert.notNull(socialUser, "社交用户不能为空");

        // 社交用户可能之前绑定过别的用户，需要进行解绑
        systemSocialUserBindRepository.deleteByUserTypeAndSocialUserId(reqDTO.getUserType(), socialUser.id());

        // 用户可能之前已经绑定过该社交类型，需要进行解绑
        systemSocialUserBindRepository.deleteByUserTypeAndUserIdAndSocialType(reqDTO.getUserType(), reqDTO.getUserId(),
                socialUser.type());

        // 绑定当前登录的社交用户
        SystemSocialUserBind socialUserBind = SystemSocialUserBindDraft.$.produce(SystemSocialUserBind->{
            SystemSocialUserBind.setUserId(reqDTO.getUserId()).setUserType(reqDTO.getUserType())
                    .setSocialUserId(socialUser.id()).setSocialType(socialUser.type());
        });
                /*SystemSocialUserBind.builder()
                .userId(reqDTO.getUserId()).userType(reqDTO.getUserType())
                .socialUserId(socialUser.getId()).socialType(socialUser.getType()).build();*/
        systemSocialUserBindRepository.insert(socialUserBind);
    }

    @Override
    public void unbindSocialUser(Long userId, Integer userType, Integer type, String openid) {
        // 获得 openid 对应的 SystemSocialUser 社交用户
        Optional<SystemSocialUser> opSocialUser = systemSocialUserRepository.findByTypeAndOpenid(type, openid);
        if (!opSocialUser.isPresent()) {
            throw exception(SOCIAL_USER_NOT_FOUND);
        }

        // 获得对应的社交绑定关系
        systemSocialUserBindRepository.deleteByUserTypeAndUserIdAndSocialType(userType, userId, opSocialUser.get().type());
    }

    @Override
    public Long getBindUserId(Integer userType, Integer type, String code, String state) {
        // 获得社交用户
        SystemSocialUser socialUser = authSocialUser(type, code, state);
        Assert.notNull(socialUser, "社交用户不能为空");

        // 如果未绑定的社交用户，则无法自动登录，进行报错
        Optional<SystemSocialUserBind> opSocialUserBind = systemSocialUserBindRepository.findByUserTypeAndSocialUserId(userType,
                socialUser.id());
        if (!opSocialUserBind.isPresent()) {
            throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
        }
        return opSocialUserBind.get().userId();
    }

    /**
     * 请求社交平台，获得授权的用户
     *
     * @param type 社交平台的类型
     * @param code 授权码
     * @param state 授权 state
     * @return 授权的用户
     */
    private AuthUser getAuthUser(Integer type, String code, String state) {
        AuthRequest authRequest = yudaoAuthRequestFactory.get(SocialTypeEnum.valueOfType(type).getSource());
        AuthCallback authCallback = AuthCallback.builder().code(code).state(state).build();
        AuthResponse<?> authResponse = authRequest.login(authCallback);
        log.info("[getAuthUser][请求社交平台 type({}) request({}) response({})]", type,
                toJsonString(authCallback), toJsonString(authResponse));
        if (!authResponse.ok()) {
            throw exception(SOCIAL_USER_AUTH_FAILURE, authResponse.getMsg());
        }
        return (AuthUser) authResponse.getData();
    }

}
