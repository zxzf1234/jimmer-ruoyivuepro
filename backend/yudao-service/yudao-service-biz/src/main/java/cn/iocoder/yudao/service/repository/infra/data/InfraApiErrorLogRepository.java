package cn.iocoder.yudao.service.repository.infra.data;

import cn.iocoder.yudao.service.vo.infra.logger.apierrorlog.ApiErrorLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.apierrorlog.ApiErrorLogPageReqVO;
import cn.iocoder.yudao.service.model.infra.data.InfraApiErrorLog;
import cn.iocoder.yudao.service.model.infra.data.InfraApiErrorLogTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;

public interface InfraApiErrorLogRepository extends JRepository<InfraApiErrorLog, Long> {
    InfraApiErrorLogTable  infraApiErrorLogTable = InfraApiErrorLogTable.$;

    default Page<InfraApiErrorLog> selectPage(ApiErrorLogPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(infraApiErrorLogTable)
                        .whereIf(reqVO.getProcessStatus() != null, infraApiErrorLogTable.processStatus().eq(reqVO.getProcessStatus()))
                        .whereIf(StringUtils.hasText(reqVO.getApplicationName()), infraApiErrorLogTable.applicationName().eq(reqVO.getApplicationName()))
                        .whereIf(StringUtils.hasText(reqVO.getRequestUrl()), infraApiErrorLogTable.requestUrl().eq(reqVO.getRequestUrl()))
                        .whereIf(reqVO.getExceptionTime() != null, ()-> infraApiErrorLogTable.exceptionTime().between(reqVO.getExceptionTime()[0], reqVO.getExceptionTime()[1]))
                        .whereIf(reqVO.getUserId() != null, infraApiErrorLogTable.userId().eq(reqVO.getUserId()))
                        .whereIf(reqVO.getUserType() != null, infraApiErrorLogTable.userType().eq(reqVO.getUserType()))
                        .select(infraApiErrorLogTable)
        );
    }

    default List<InfraApiErrorLog> selectList(ApiErrorLogExportReqVO reqVO){
        return sql()
                .createQuery(infraApiErrorLogTable)
                .whereIf(reqVO.getProcessStatus() != null, infraApiErrorLogTable.processStatus().eq(reqVO.getProcessStatus()))
                .whereIf(StringUtils.hasText(reqVO.getApplicationName()), infraApiErrorLogTable.applicationName().eq(reqVO.getApplicationName()))
                .whereIf(StringUtils.hasText(reqVO.getRequestUrl()), infraApiErrorLogTable.requestUrl().eq(reqVO.getRequestUrl()))
                .whereIf(reqVO.getExceptionTime() != null, ()-> infraApiErrorLogTable.exceptionTime().between(reqVO.getExceptionTime()[0], reqVO.getExceptionTime()[1]))
                .whereIf(reqVO.getUserId() != null, infraApiErrorLogTable.userId().eq(reqVO.getUserId()))
                .whereIf(reqVO.getUserType() != null, infraApiErrorLogTable.userType().eq(reqVO.getUserType()))
                .select(infraApiErrorLogTable)
                .execute();
    }
}
