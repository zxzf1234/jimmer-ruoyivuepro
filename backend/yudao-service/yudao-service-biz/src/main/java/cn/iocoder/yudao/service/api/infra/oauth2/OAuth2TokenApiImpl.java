package cn.iocoder.yudao.service.api.infra.oauth2;

import cn.iocoder.yudao.service.api.infra.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import cn.iocoder.yudao.service.api.infra.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import cn.iocoder.yudao.service.api.infra.oauth2.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.yudao.service.convert.infra.auth.OAuth2TokenConvert;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2AccessToken;
import cn.iocoder.yudao.service.service.infra.oauth2.OAuth2TokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * OAuth2.0 Token API 实现类
 *
 * @author 芋道源码
 */
@Service
public class OAuth2TokenApiImpl implements OAuth2TokenApi {

    @Resource
    private OAuth2TokenService oauth2TokenService;

    @Override
    public OAuth2AccessTokenRespDTO createAccessToken(OAuth2AccessTokenCreateReqDTO reqDTO) {
        SystemOauth2AccessToken accessTokenDO = oauth2TokenService.createAccessToken(
                reqDTO.getUserId(), reqDTO.getUserType(), reqDTO.getClientId(), reqDTO.getScopes());
        return OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO);
    }

    @Override
    public OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken) {
        return OAuth2TokenConvert.INSTANCE.convert(oauth2TokenService.checkAccessToken(accessToken));
    }

    @Override
    public OAuth2AccessTokenRespDTO removeAccessToken(String accessToken) {
        SystemOauth2AccessToken accessTokenDO = oauth2TokenService.removeAccessToken(accessToken);
        return OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO);
    }

    @Override

    public OAuth2AccessTokenRespDTO refreshAccessToken(String refreshToken, String clientId) {
        SystemOauth2AccessToken accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, clientId);
        return OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO);
    }

}
