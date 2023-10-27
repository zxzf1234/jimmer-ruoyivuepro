package cn.iocoder.yudao.service.repository.infra.sms;

import cn.iocoder.yudao.service.vo.infra.sms.template.SmsTemplateExportReqVO;
import cn.iocoder.yudao.service.vo.infra.sms.template.SmsTemplatePageReqVO;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsTemplate;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsTemplateTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public interface SystemSmsTemplateRepository extends JRepository<SystemSmsTemplate,Long> {
    SystemSmsTemplateTable systemSmsTemplateTable = SystemSmsTemplateTable.$;

    default Page<SystemSmsTemplate> selectPage(SmsTemplatePageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemSmsTemplateTable)
                        .whereIf(StringUtils.hasText(reqVO.getApiTemplateId()), systemSmsTemplateTable.apiTemplateId().eq(reqVO.getApiTemplateId()))
                        .whereIf(StringUtils.hasText(reqVO.getCode()), systemSmsTemplateTable.code().eq(reqVO.getCode()))
                        .whereIf(reqVO.getChannelId() != null, systemSmsTemplateTable.channelId().eq(reqVO.getChannelId()))
                        .whereIf(reqVO.getStatus() != null, systemSmsTemplateTable.status().eq(reqVO.getStatus()))
                        .whereIf(reqVO.getType() != null, systemSmsTemplateTable.type().eq(reqVO.getType()))
                        .whereIf(StringUtils.hasText(reqVO.getContent()), systemSmsTemplateTable.content().eq(reqVO.getContent()))
                        .whereIf(reqVO.getCreateTime() != null, ()-> systemSmsTemplateTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(systemSmsTemplateTable)
        );
    }

    default List<SystemSmsTemplate> selectList(SmsTemplateExportReqVO reqVO){
        return sql()
                .createQuery(systemSmsTemplateTable)
                .whereIf(StringUtils.hasText(reqVO.getApiTemplateId()), systemSmsTemplateTable.apiTemplateId().eq(reqVO.getApiTemplateId()))
                .whereIf(StringUtils.hasText(reqVO.getCode()), systemSmsTemplateTable.code().eq(reqVO.getCode()))
                .whereIf(reqVO.getChannelId() != null, systemSmsTemplateTable.channelId().eq(reqVO.getChannelId()))
                .whereIf(reqVO.getStatus() != null, systemSmsTemplateTable.status().eq(reqVO.getStatus()))
                .whereIf(reqVO.getType() != null, systemSmsTemplateTable.type().eq(reqVO.getType()))
                .whereIf(StringUtils.hasText(reqVO.getContent()), systemSmsTemplateTable.content().eq(reqVO.getContent()))
                .whereIf(reqVO.getCreateTime() != null, ()-> systemSmsTemplateTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                .select(systemSmsTemplateTable)
                .execute();
    }

    Optional<SystemSmsTemplate> findByName(String name);

    long countByChannelId(Long channelId);

    Optional<SystemSmsTemplate> findByCode(String code);
}
