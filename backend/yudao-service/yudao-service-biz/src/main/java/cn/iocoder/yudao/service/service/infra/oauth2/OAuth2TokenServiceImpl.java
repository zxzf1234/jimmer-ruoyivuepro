package cn.iocoder.yudao.service.service.infra.oauth2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.date.DateUtils;
import cn.iocoder.yudao.service.vo.infra.oauth2.token.OAuth2AccessTokenPageReqVO;
import cn.iocoder.yudao.service.vo.infra.oauth2.token.OAuth2AccessTokenRespVO;
import cn.iocoder.yudao.service.convert.infra.auth.OAuth2TokenConvert;
import cn.iocoder.yudao.service.dal.redis.oauth2.OAuth2AccessTokenRedisDAO;
import cn.iocoder.yudao.service.model.infra.oauth2.*;
import cn.iocoder.yudao.service.repository.infra.oauth2.SystemOauth2AccessTokenRepository;
import cn.iocoder.yudao.service.repository.infra.oauth2.SystemOauth2RefreshTokenRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception0;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * OAuth2.0 Token Service 实现类
 *
 * @author 芋道源码
 */
@Service
public class OAuth2TokenServiceImpl implements OAuth2TokenService {

    @Resource
    private SystemOauth2AccessTokenRepository systemOauth2AccessTokenRepository;
    @Resource
    private SystemOauth2RefreshTokenRepository systemOauth2RefreshTokenRepository;

    @Resource
    private OAuth2AccessTokenRedisDAO oauth2AccessTokenRedisDAO;

    @Resource
    private OAuth2ClientService oauth2ClientService;

    @Override
    @Transactional
    public SystemOauth2AccessToken createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes) {
        SystemOauth2Client clientDO = oauth2ClientService.validOAuthClientFromCache(clientId);
        // 创建刷新令牌
        SystemOauth2RefreshToken refreshTokenDO = createOAuth2RefreshToken(userId, userType, clientDO, scopes);
        // 创建访问令牌
        return createOAuth2AccessToken(refreshTokenDO, clientDO);
    }

    @Override
    public SystemOauth2AccessToken refreshAccessToken(String refreshToken, String clientId) {
        // 查询访问令牌
        Optional<SystemOauth2RefreshToken> opRefreshTokenDO = systemOauth2RefreshTokenRepository.findByRefreshToken(refreshToken);
        if (!opRefreshTokenDO.isPresent()) {
            throw exception0(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "无效的刷新令牌");
        }

        // 校验 Client 匹配
        SystemOauth2Client clientDO = oauth2ClientService.validOAuthClientFromCache(clientId);
        if (ObjectUtil.notEqual(clientId, opRefreshTokenDO.get().clientId())) {
            throw exception0(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "刷新令牌的客户端编号不正确");
        }

        // 移除相关的访问令牌
        List<SystemOauth2AccessToken> accessTokenDOs = systemOauth2AccessTokenRepository.findByRefreshToken(refreshToken);
        if (CollUtil.isNotEmpty(accessTokenDOs)) {
            systemOauth2AccessTokenRepository.deleteByIds(convertSet(accessTokenDOs, SystemOauth2AccessToken::id));
            oauth2AccessTokenRedisDAO.deleteList(convertSet(accessTokenDOs, SystemOauth2AccessToken::accessToken));
        }

        // 已过期的情况下，删除刷新令牌
        if (DateUtils.isExpired(opRefreshTokenDO.get().expiresTime())) {
            systemOauth2RefreshTokenRepository.deleteById(opRefreshTokenDO.get().id());
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "刷新令牌已过期");
        }

        // 创建访问令牌
        return createOAuth2AccessToken(opRefreshTokenDO.get(), clientDO);
    }

    @Override
    public SystemOauth2AccessToken getAccessToken(String accessToken) {
        // 优先从 Redis 中获取
        SystemOauth2AccessToken accessTokenDO = oauth2AccessTokenRedisDAO.get(accessToken);
        if (accessTokenDO != null) {
            return accessTokenDO;
        }

        // 获取不到，从 MySQL 中获取
        Optional<SystemOauth2AccessToken> opAccessToken = systemOauth2AccessTokenRepository.findByAccessToken(accessToken);
        // 如果在 MySQL 存在，则往 Redis 中写入
        if (opAccessToken.isPresent() && !DateUtils.isExpired(opAccessToken.get().expiresTime())) {
            oauth2AccessTokenRedisDAO.set(opAccessToken.get());
        }
        return opAccessToken.orElse(null);
    }

    @Override
    public SystemOauth2AccessToken checkAccessToken(String accessToken) {
        SystemOauth2AccessToken accessTokenDO = getAccessToken(accessToken);
        if (accessTokenDO == null) {
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "访问令牌不存在");
        }
        if (DateUtils.isExpired(accessTokenDO.expiresTime())) {
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "访问令牌已过期");
        }
        return accessTokenDO;
    }

    @Override
    public SystemOauth2AccessToken removeAccessToken(String accessToken) {
        // 删除访问令牌
        Optional<SystemOauth2AccessToken> opAccessTokenDO = systemOauth2AccessTokenRepository.findByAccessToken(accessToken);
        if (!opAccessTokenDO.isPresent()) {
            return null;
        }
        systemOauth2AccessTokenRepository.deleteById(opAccessTokenDO.get().id());
        oauth2AccessTokenRedisDAO.delete(accessToken);
        // 删除刷新令牌
        systemOauth2RefreshTokenRepository.deleteByRefreshToken(opAccessTokenDO.get().refreshToken());
        return opAccessTokenDO.get();
    }

    @Override
    public PageResult<OAuth2AccessTokenRespVO> getAccessTokenPage(OAuth2AccessTokenPageReqVO reqVO) {
        Page<SystemOauth2AccessToken> postPage = systemOauth2AccessTokenRepository.selectPage(reqVO);
        List<OAuth2AccessTokenRespVO> listPage = OAuth2TokenConvert.INSTANCE.convert(postPage);
        return new PageResult<>(listPage, postPage.getTotalElements());
    }

    private SystemOauth2AccessToken createOAuth2AccessToken(SystemOauth2RefreshToken refreshTokenDO, SystemOauth2Client clientDO) {
        SystemOauth2AccessToken accessTokenDO = SystemOauth2AccessTokenDraft.$.produce(SystemOauth2AccessToken->{
            SystemOauth2AccessToken.setAccessToken(generateAccessToken())
                    .setUserId(refreshTokenDO.userId()).setUserType(refreshTokenDO.userType())
                    .setClientId(clientDO.clientId()).setScopes(refreshTokenDO.scopes())
                    .setRefreshToken(refreshTokenDO.refreshToken()).setExpiresTime(LocalDateTime.now().plusSeconds(clientDO.accessTokenValiditySeconds()));
        });
        accessTokenDO = systemOauth2AccessTokenRepository.insert(accessTokenDO);
        // 记录到 Redis 中
        oauth2AccessTokenRedisDAO.set(accessTokenDO);
        return accessTokenDO;
    }

    private SystemOauth2RefreshToken createOAuth2RefreshToken(Long userId, Integer userType, SystemOauth2Client clientDO, List<String> scopes) {
        SystemOauth2RefreshToken refreshToken = SystemOauth2RefreshTokenDraft.$.produce(SystemOauth2RefreshToken->{
            SystemOauth2RefreshToken.setRefreshToken(generateAccessToken())
                    .setUserId(userId).setUserType(userType)
                    .setClientId(clientDO.clientId()).setScopes(scopes)
                    .setRefreshToken(generateRefreshToken()).setExpiresTime(LocalDateTime.now().plusSeconds(clientDO.refreshTokenValiditySeconds()));
        });

        refreshToken = systemOauth2RefreshTokenRepository.insert(refreshToken);
        return refreshToken;
    }

    private static String generateAccessToken() {
        return IdUtil.fastSimpleUUID();
    }

    private static String generateRefreshToken() {
        return IdUtil.fastSimpleUUID();
    }

}
