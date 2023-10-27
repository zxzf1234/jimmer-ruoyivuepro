package cn.iocoder.yudao.service.repository.infra.oauth2;

import cn.iocoder.yudao.service.vo.infra.oauth2.client.OAuth2ClientPageReqVO;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2Client;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2ClientTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.Optional;

public interface SystemOauth2ClientRepository extends JRepository<SystemOauth2Client, Long>{
    SystemOauth2ClientTable systemOauth2ClientTable = SystemOauth2ClientTable.$;

    default Page<SystemOauth2Client> selectPage(OAuth2ClientPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemOauth2ClientTable)
                        .whereIf(reqVO.getStatus() != null, systemOauth2ClientTable.status().eq(reqVO.getStatus()))
                        .whereIf(StringUtils.hasText(reqVO.getName()), systemOauth2ClientTable.name().eq(reqVO.getName()))
                        .select(systemOauth2ClientTable)
        );
    }

    Optional<SystemOauth2Client> findByClientId(String clientId);
}
