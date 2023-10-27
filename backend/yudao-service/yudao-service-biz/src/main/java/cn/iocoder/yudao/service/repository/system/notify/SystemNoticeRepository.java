package cn.iocoder.yudao.service.repository.system.notify;

import cn.iocoder.yudao.service.vo.system.notify.notice.NoticePageReqVO;
import cn.iocoder.yudao.service.model.system.notify.SystemNotice;
import cn.iocoder.yudao.service.model.system.notify.SystemNoticeTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

public interface SystemNoticeRepository extends JRepository<SystemNotice, Long> {
    SystemNoticeTable systemNoticeTable = SystemNoticeTable.$;
    default Page<SystemNotice> selectPage(NoticePageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemNoticeTable)
                        .whereIf(reqVO.getStatus() != null, systemNoticeTable.status().eq(reqVO.getStatus()))
                        .whereIf(StringUtils.hasText(reqVO.getTitle()), systemNoticeTable.title().eq(reqVO.getTitle()))
                        .select(systemNoticeTable)
        );
    }
}
