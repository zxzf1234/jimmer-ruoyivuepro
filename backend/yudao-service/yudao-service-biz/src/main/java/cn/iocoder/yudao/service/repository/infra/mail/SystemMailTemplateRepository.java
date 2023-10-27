package cn.iocoder.yudao.service.repository.infra.mail;

import cn.iocoder.yudao.service.vo.infra.mail.template.MailTemplatePageReqVO;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailTemplate;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailTemplateTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.Optional;

public interface SystemMailTemplateRepository extends JRepository<SystemMailTemplate, Long> {
    SystemMailTemplateTable systemMailTemplateTable = SystemMailTemplateTable.$;

    default Page<SystemMailTemplate> selectPage(MailTemplatePageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemMailTemplateTable)
                        .whereIf(reqVO.getAccountId() != null, systemMailTemplateTable.accountId().eq(reqVO.getAccountId()))
                        .whereIf(StringUtils.hasText(reqVO.getCode()), systemMailTemplateTable.code().eq(reqVO.getCode()))
                        .whereIf(StringUtils.hasText(reqVO.getName()), systemMailTemplateTable.name().eq(reqVO.getName()))
                        .whereIf(reqVO.getStatus() != null, systemMailTemplateTable.status().eq(reqVO.getStatus()))
                        .whereIf(reqVO.getCreateTime() != null && reqVO.getCreateTime().length > 0, () -> systemMailTemplateTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(systemMailTemplateTable)
        );
    }

    Optional<SystemMailTemplate> findByCode(String code);

    long countByAccountId(Long accountId);
}
