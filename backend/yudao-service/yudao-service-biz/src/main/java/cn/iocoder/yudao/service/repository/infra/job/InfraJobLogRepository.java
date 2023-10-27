package cn.iocoder.yudao.service.repository.infra.job;

import cn.iocoder.yudao.service.vo.infra.job.log.JobLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.job.log.JobLogPageReqVO;
import cn.iocoder.yudao.service.model.infra.job.InfraJobLog;
import cn.iocoder.yudao.service.model.infra.job.InfraJobLogTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;

public interface InfraJobLogRepository extends JRepository<InfraJobLog, Long> {
    InfraJobLogTable infraJobLogTable = InfraJobLogTable.$;

    default Page<InfraJobLog> selectPage(JobLogPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(infraJobLogTable)
                        .whereIf(reqVO.getJobId() != null, infraJobLogTable.jobId().eq(reqVO.getJobId()))
                        .whereIf(StringUtils.hasText(reqVO.getHandlerName()), infraJobLogTable.handlerName().eq(reqVO.getHandlerName()))
                        .whereIf(reqVO.getStatus() != null, infraJobLogTable.status().eq(reqVO.getStatus()))
                        .whereIf(reqVO.getBeginTime() != null, infraJobLogTable.beginTime().eq(reqVO.getBeginTime()))
                        .whereIf(reqVO.getEndTime() !=null, infraJobLogTable.endTime().eq(reqVO.getEndTime()))
                        .select(infraJobLogTable)
        );
    }

    default List<InfraJobLog> selectList(JobLogExportReqVO reqVO) {
        return sql()
                .createQuery(infraJobLogTable)
                .whereIf(reqVO.getJobId() != null, infraJobLogTable.jobId().eq(reqVO.getJobId()))
                .whereIf(StringUtils.hasText(reqVO.getHandlerName()), infraJobLogTable.handlerName().eq(reqVO.getHandlerName()))
                .whereIf(reqVO.getStatus() != null, infraJobLogTable.status().eq(reqVO.getStatus()))
                .whereIf(reqVO.getBeginTime() != null, infraJobLogTable.beginTime().eq(reqVO.getBeginTime()))
                .whereIf(reqVO.getEndTime() !=null, infraJobLogTable.endTime().eq(reqVO.getEndTime()))
                .select(infraJobLogTable)
                .execute();
    }
}
