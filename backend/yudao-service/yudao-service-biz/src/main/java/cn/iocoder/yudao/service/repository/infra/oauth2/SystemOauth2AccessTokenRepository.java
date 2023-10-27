package cn.iocoder.yudao.service.repository.infra.oauth2;

import cn.iocoder.yudao.service.vo.infra.oauth2.token.OAuth2AccessTokenPageReqVO;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2AccessToken;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2AccessTokenTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public interface SystemOauth2AccessTokenRepository extends JRepository<SystemOauth2AccessToken, Long> {
    SystemOauth2AccessTokenTable systemOauth2AccessTokenTable = SystemOauth2AccessTokenTable.$;

    default Page<SystemOauth2AccessToken> selectPage(OAuth2AccessTokenPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql().createQuery(systemOauth2AccessTokenTable)
                        .whereIf(reqVO.getUserId() != null, systemOauth2AccessTokenTable.userId().eq(reqVO.getUserId()))
                        .whereIf(reqVO.getUserType() != null, systemOauth2AccessTokenTable.userType().eq(reqVO.getUserType()))
                        .whereIf(StringUtils.hasText(reqVO.getClientId()), systemOauth2AccessTokenTable.clientId().eq(reqVO.getClientId()))
                        .select(systemOauth2AccessTokenTable)
        );
    }

    List<SystemOauth2AccessToken> findByRefreshToken(String refreshToken);

    Optional<SystemOauth2AccessToken> findByAccessToken(String accessToken);
}
