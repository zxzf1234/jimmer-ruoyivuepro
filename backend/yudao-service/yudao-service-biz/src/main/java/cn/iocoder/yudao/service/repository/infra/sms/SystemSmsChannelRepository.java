package cn.iocoder.yudao.service.repository.infra.sms;

import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelPageReqVO;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsChannel;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsChannelTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

public interface SystemSmsChannelRepository extends JRepository<SystemSmsChannel, Long> {
    SystemSmsChannelTable systemSmsChannelTable = SystemSmsChannelTable.$;

    default Page<SystemSmsChannel> selectPage(SmsChannelPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemSmsChannelTable)
                        .whereIf(reqVO.getStatus() != null, systemSmsChannelTable.status().eq(reqVO.getStatus()))
                        .whereIf(StringUtils.hasText(reqVO.getSignature()), systemSmsChannelTable.signature().eq(reqVO.getSignature()))
                        .whereIf(reqVO.getCreateTime() != null, ()-> systemSmsChannelTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(systemSmsChannelTable)
        );
    }
}
