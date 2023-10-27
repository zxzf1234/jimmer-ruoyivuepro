package cn.iocoder.yudao.service.repository.infra.mail;

import cn.iocoder.yudao.service.vo.infra.mail.log.MailLogPageReqVO;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailLog;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailLogTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

public interface SystemMailLogRepository extends JRepository<SystemMailLog, Long> {

    SystemMailLogTable systemMailLogTable = SystemMailLogTable.$;

    default Page<SystemMailLog> selectPage(MailLogPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemMailLogTable)
                        .whereIf(reqVO.getAccountId() != null, systemMailLogTable.accountId().eq(reqVO.getAccountId()))
                        .whereIf(StringUtils.hasText(reqVO.getToMail()), systemMailLogTable.toMail().eq(reqVO.getToMail()))
                        .whereIf(reqVO.getUserId() != null, systemMailLogTable.userId().eq(reqVO.getUserId()))
                        .whereIf(reqVO.getSendStatus() != null, systemMailLogTable.sendStatus().eq(reqVO.getSendStatus()))
                        .whereIf(reqVO.getTemplateId() != null, systemMailLogTable.templateId().eq(reqVO.getTemplateId()))
                        .whereIf(reqVO.getUserType() != null, systemMailLogTable.userType().eq(reqVO.getUserType()))
                        .whereIf(reqVO.getSendTime() != null && reqVO.getSendTime().length > 0,  () -> systemMailLogTable.createTime().between(reqVO.getSendTime()[0], reqVO.getSendTime()[1]))
                        .select(systemMailLogTable)
        );
    }
}
