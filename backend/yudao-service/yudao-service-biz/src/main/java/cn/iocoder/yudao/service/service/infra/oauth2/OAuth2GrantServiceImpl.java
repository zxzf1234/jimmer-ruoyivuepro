package cn.iocoder.yudao.service.service.infra.oauth2;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.service.enums.system.ErrorCodeConstants;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2AccessToken;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2Code;
import cn.iocoder.yudao.service.model.system.user.SystemUser;
import cn.iocoder.yudao.service.service.infra.auth.AdminAuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * OAuth2 授予 Service 实现类
 *
 * @author 芋道源码
 */
@Service
public class OAuth2GrantServiceImpl implements OAuth2GrantService {

    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private OAuth2CodeService oauth2CodeService;
    @Resource
    private AdminAuthService adminAuthService;

    @Override
    public SystemOauth2AccessToken grantImplicit(Long userId, Integer userType,
                                                 String clientId, List<String> scopes) {
        return oauth2TokenService.createAccessToken(userId, userType, clientId, scopes);
    }

    @Override
    public String grantAuthorizationCodeForCode(Long userId, Integer userType,
                                                String clientId, List<String> scopes,
                                                String redirectUri, String state) {
        return oauth2CodeService.createAuthorizationCode(userId, userType, clientId, scopes,
                redirectUri, state).code();
    }

    @Override
    public SystemOauth2AccessToken grantAuthorizationCodeForAccessToken(String clientId, String code,
                                                                    String redirectUri, String state) {
        SystemOauth2Code codeDO = oauth2CodeService.consumeAuthorizationCode(code);
        Assert.notNull(codeDO, "授权码不能为空"); // 防御性编程
        // 校验 clientId 是否匹配
        if (!StrUtil.equals(clientId, codeDO.clientId())) {
            throw exception(ErrorCodeConstants.OAUTH2_GRANT_CLIENT_ID_MISMATCH);
        }
        // 校验 redirectUri 是否匹配
        if (!StrUtil.equals(redirectUri, codeDO.redirectUri())) {
            throw exception(ErrorCodeConstants.OAUTH2_GRANT_REDIRECT_URI_MISMATCH);
        }
        // 校验 state 是否匹配
        state = StrUtil.nullToDefault(state, ""); // 数据库 state 为 null 时，会设置为 "" 空串
        if (!StrUtil.equals(state, codeDO.state())) {
            throw exception(ErrorCodeConstants.OAUTH2_GRANT_STATE_MISMATCH);
        }

        // 创建访问令牌
        return oauth2TokenService.createAccessToken(codeDO.userId(), codeDO.userType(),
                codeDO.clientId(), codeDO.scopes());
    }

    @Override
    public SystemOauth2AccessToken grantPassword(String username, String password, String clientId, List<String> scopes) {
        // 使用账号 + 密码进行登录
        SystemUser user = adminAuthService.authenticate(username, password);
        Assert.notNull(user, "用户不能为空！"); // 防御性编程

        // 创建访问令牌
        return oauth2TokenService.createAccessToken(user.id(), UserTypeEnum.ADMIN.getValue(), clientId, scopes);
    }

    @Override
    public SystemOauth2AccessToken grantRefreshToken(String refreshToken, String clientId) {
        return oauth2TokenService.refreshAccessToken(refreshToken, clientId);
    }

    @Override
    public SystemOauth2AccessToken grantClientCredentials(String clientId, List<String> scopes) {
        // TODO 芋艿：项目中使用 OAuth2 解决的是三方应用的授权，内部的 SSO 等问题，所以暂时不考虑 client_credentials 这个场景
        throw new UnsupportedOperationException("暂时不支持 client_credentials 授权模式");
    }

    @Override
    public boolean revokeToken(String clientId, String accessToken) {
        // 先查询，保证 clientId 时匹配的
        SystemOauth2AccessToken accessTokenDO = oauth2TokenService.getAccessToken(accessToken);
        if (accessTokenDO == null || ObjectUtil.notEqual(clientId, accessTokenDO.clientId())) {
            return false;
        }
        // 再删除
        return oauth2TokenService.removeAccessToken(accessToken) != null;
    }

}
