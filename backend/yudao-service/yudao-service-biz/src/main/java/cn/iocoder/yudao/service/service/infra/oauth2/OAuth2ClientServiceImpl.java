package cn.iocoder.yudao.service.service.infra.oauth2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.string.StrUtils;
import cn.iocoder.yudao.service.vo.infra.oauth2.client.OAuth2ClientCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.oauth2.client.OAuth2ClientPageReqVO;
import cn.iocoder.yudao.service.vo.infra.oauth2.client.OAuth2ClientRespVO;
import cn.iocoder.yudao.service.vo.infra.oauth2.client.OAuth2ClientUpdateReqVO;
import cn.iocoder.yudao.service.convert.infra.auth.OAuth2ClientConvert;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2Client;
import cn.iocoder.yudao.service.repository.infra.oauth2.SystemOauth2ClientRepository;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.*;

/**
 * OAuth2.0 Client Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class OAuth2ClientServiceImpl implements OAuth2ClientService {

    @Resource
    private SystemOauth2ClientRepository systemOauth2ClientRepository;


    @Override
    public Long createOAuth2Client(OAuth2ClientCreateReqVO createReqVO) {
        validateClientIdExists(null, createReqVO.getClientId());
        // 插入
        SystemOauth2Client oauth2Client = OAuth2ClientConvert.INSTANCE.convert(createReqVO);
        oauth2Client = systemOauth2ClientRepository.insert(oauth2Client);
        return oauth2Client.id();
    }

    @Override
    public void updateOAuth2Client(OAuth2ClientUpdateReqVO updateReqVO) {
        // 校验存在
        validateOAuth2ClientExists(updateReqVO.getId());
        // 校验 Client 未被占用
        validateClientIdExists(updateReqVO.getId(), updateReqVO.getClientId());

        // 更新
        SystemOauth2Client updateObj = OAuth2ClientConvert.INSTANCE.convert(updateReqVO);
        systemOauth2ClientRepository.update(updateObj);
    }

    @Override
    public void deleteOAuth2Client(Long id) {
        // 校验存在
        validateOAuth2ClientExists(id);
        // 删除
        systemOauth2ClientRepository.deleteById(id);
    }

    private void validateOAuth2ClientExists(Long id) {
        if (!systemOauth2ClientRepository.findById(id).isPresent()) {
            throw exception(OAUTH2_CLIENT_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    void validateClientIdExists(Long id, String clientId) {
        Optional<SystemOauth2Client> opClient = systemOauth2ClientRepository.findByClientId(clientId);
        if (!opClient.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的客户端
        if (id == null) {
            throw exception(OAUTH2_CLIENT_EXISTS);
        }
        if (opClient.get().id() != id) {
            throw exception(OAUTH2_CLIENT_EXISTS);
        }
    }

    @Override
    public SystemOauth2Client getOAuth2Client(Long id) {
        return systemOauth2ClientRepository.findById(id).get();
    }

    @Override
    public PageResult<OAuth2ClientRespVO> getOAuth2ClientPage(OAuth2ClientPageReqVO pageReqVO) {
        Page<SystemOauth2Client> postPage = systemOauth2ClientRepository.selectPage(pageReqVO);
        List<OAuth2ClientRespVO> postList = OAuth2ClientConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(postList, postPage.getTotalElements());
    }

    @Override
    public SystemOauth2Client validOAuthClientFromCache(String clientId, String clientSecret,
                                                    String authorizedGrantType, Collection<String> scopes, String redirectUri) {
        // 校验客户端存在、且开启
        Optional<SystemOauth2Client> opClient = systemOauth2ClientRepository.findByClientId(clientId);
        if(!opClient.isPresent()){
            throw exception(OAUTH2_CLIENT_NOT_EXISTS);
        }
        SystemOauth2Client client = opClient.get();
        if (ObjectUtil.notEqual(client.status(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(OAUTH2_CLIENT_DISABLE);
        }

        // 校验客户端密钥
        if (StrUtil.isNotEmpty(clientSecret) && ObjectUtil.notEqual(client.secret(), clientSecret)) {
            throw exception(OAUTH2_CLIENT_CLIENT_SECRET_ERROR);
        }
        // 校验授权方式
        if (StrUtil.isNotEmpty(authorizedGrantType) && !CollUtil.contains(client.authorizedGrantTypes(), authorizedGrantType)) {
            throw exception(OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS);
        }
        // 校验授权范围
        if (CollUtil.isNotEmpty(scopes) && !CollUtil.containsAll(client.scopes(), scopes)) {
            throw exception(OAUTH2_CLIENT_SCOPE_OVER);
        }
        // 校验回调地址
        if (StrUtil.isNotEmpty(redirectUri) && !StrUtils.startWithAny(redirectUri, client.redirectUris())) {
            throw exception(OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH, redirectUri);
        }
        return client;
    }

}
