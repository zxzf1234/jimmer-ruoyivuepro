package cn.iocoder.yudao.service.repository.system.notify;

import cn.iocoder.yudao.service.vo.system.notify.template.NotifyTemplatePageReqVO;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyTemplate;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyTemplateTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.Optional;

public interface SystemNotifyTemplateRepository extends JRepository<SystemNotifyTemplate, Long> {
    SystemNotifyTemplateTable systemNotifyTemplateTable = SystemNotifyTemplateTable.$;

    default Page<SystemNotifyTemplate> selectPage(NotifyTemplatePageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemNotifyTemplateTable)
                        .whereIf(StringUtils.hasText(reqVO.getCode()), systemNotifyTemplateTable.code().eq(reqVO.getCode()))
                        .whereIf(StringUtils.hasText(reqVO.getName()), systemNotifyTemplateTable.name().eq(reqVO.getName()))
                        .whereIf(reqVO.getStatus() != null, systemNotifyTemplateTable.status().eq(reqVO.getStatus()))
                        .whereIf(reqVO.getCreateTime() != null, ()->systemNotifyTemplateTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(systemNotifyTemplateTable)
        );
    }

    Optional<SystemNotifyTemplate> findByCode(String code);
}
