package cn.iocoder.yudao.service.repository.infra.sms;

import cn.iocoder.yudao.service.vo.infra.sms.log.SmsLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.sms.log.SmsLogPageReqVO;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsLog;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsLogTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;

public interface SystemSmsLogRepository extends JRepository<SystemSmsLog, Long> {
    SystemSmsLogTable systemSmsLogTable = SystemSmsLogTable.$;
    default Page<SystemSmsLog> selectPage(SmsLogPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemSmsLogTable)
                        .whereIf(reqVO.getChannelId() != null, systemSmsLogTable.channelId().eq(reqVO.getChannelId()))
                        .whereIf(reqVO.getReceiveStatus() != null, systemSmsLogTable.receiveStatus().eq(reqVO.getReceiveStatus()))
                        .whereIf(reqVO.getTemplateId() != null, systemSmsLogTable.templateId().eq(reqVO.getTemplateId()))
                        .whereIf(StringUtils.hasText(reqVO.getMobile()), systemSmsLogTable.mobile().eq(reqVO.getMobile()))
                        .whereIf(reqVO.getReceiveTime() != null, ()-> systemSmsLogTable.receiveTime().between(reqVO.getReceiveTime()[0], reqVO.getReceiveTime()[2]))
                        .whereIf(reqVO.getSendTime() != null, ()-> systemSmsLogTable.sendTime().between(reqVO.getSendTime()[0], reqVO.getSendTime()[1]))
                        .select(systemSmsLogTable)
        );
    }

    default List<SystemSmsLog> selectList(SmsLogExportReqVO reqVO){
        return sql()
                .createQuery(systemSmsLogTable)
                .whereIf(reqVO.getChannelId() != null, systemSmsLogTable.channelId().eq(reqVO.getChannelId()))
                .whereIf(reqVO.getReceiveStatus() != null, systemSmsLogTable.receiveStatus().eq(reqVO.getReceiveStatus()))
                .whereIf(reqVO.getTemplateId() != null, systemSmsLogTable.templateId().eq(reqVO.getTemplateId()))
                .whereIf(StringUtils.hasText(reqVO.getMobile()), systemSmsLogTable.mobile().eq(reqVO.getMobile()))
                .whereIf(reqVO.getReceiveTime() != null, ()-> systemSmsLogTable.receiveTime().between(reqVO.getReceiveTime()[0], reqVO.getReceiveTime()[2]))
                .whereIf(reqVO.getSendTime() != null, ()-> systemSmsLogTable.sendTime().between(reqVO.getSendTime()[0], reqVO.getSendTime()[1]))
                .select(systemSmsLogTable)
                .execute();

    }
}
