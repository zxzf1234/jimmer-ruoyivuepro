package cn.iocoder.yudao.service.repository.infra.data;

import cn.iocoder.yudao.service.vo.infra.logger.apiaccesslog.ApiAccessLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.apiaccesslog.ApiAccessLogPageReqVO;
import cn.iocoder.yudao.service.model.infra.data.InfraApiAccessLog;
import cn.iocoder.yudao.service.model.infra.data.InfraApiAccessLogTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;

public interface InfraApiAccessLogRepository extends JRepository<InfraApiAccessLog, Long> {
    InfraApiAccessLogTable infraApiAccessLogTable = InfraApiAccessLogTable.$;

    default Page<InfraApiAccessLog> selectPage(ApiAccessLogPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(infraApiAccessLogTable)
                        .whereIf(reqVO.getDuration() != null, infraApiAccessLogTable.duration().eq(reqVO.getDuration()))
                        .whereIf(StringUtils.hasText(reqVO.getApplicationName()), infraApiAccessLogTable.applicationName().eq(reqVO.getApplicationName()))
                        .whereIf(StringUtils.hasText(reqVO.getRequestUrl()), infraApiAccessLogTable.requestUrl().eq(reqVO.getRequestUrl()))
                        .whereIf(reqVO.getBeginTime() != null, ()-> infraApiAccessLogTable.beginTime().between(reqVO.getBeginTime()[0], reqVO.getBeginTime()[1]))
                        .whereIf(reqVO.getResultCode() != null, infraApiAccessLogTable.resultCode().eq(reqVO.getResultCode()))
                        .whereIf(reqVO.getUserId() != null, infraApiAccessLogTable.userId().eq(reqVO.getUserId()))
                        .whereIf(reqVO.getUserType() != null, infraApiAccessLogTable.userType().eq(reqVO.getUserType()))
                        .select(infraApiAccessLogTable)
        );
    }

    default List<InfraApiAccessLog> selectList(ApiAccessLogExportReqVO reqVO){
        return sql()
                .createQuery(infraApiAccessLogTable)
                .whereIf(reqVO.getDuration() != null, infraApiAccessLogTable.duration().eq(reqVO.getDuration()))
                .whereIf(StringUtils.hasText(reqVO.getApplicationName()), infraApiAccessLogTable.applicationName().eq(reqVO.getApplicationName()))
                .whereIf(StringUtils.hasText(reqVO.getRequestUrl()), infraApiAccessLogTable.requestUrl().eq(reqVO.getRequestUrl()))
                .whereIf(reqVO.getBeginTime() != null, ()-> infraApiAccessLogTable.beginTime().between(reqVO.getBeginTime()[0], reqVO.getBeginTime()[1]))
                .whereIf(reqVO.getResultCode() != null, infraApiAccessLogTable.resultCode().eq(reqVO.getResultCode()))
                .whereIf(reqVO.getUserId() != null, infraApiAccessLogTable.userId().eq(reqVO.getUserId()))
                .whereIf(reqVO.getUserType() != null, infraApiAccessLogTable.userType().eq(reqVO.getUserType()))
                .select(infraApiAccessLogTable)
                .execute();
    }
}
