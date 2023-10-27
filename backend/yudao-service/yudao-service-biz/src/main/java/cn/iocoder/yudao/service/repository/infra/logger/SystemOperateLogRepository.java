package cn.iocoder.yudao.service.repository.infra.logger;

import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.service.vo.infra.logger.operatelog.OperateLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.operatelog.OperateLogPageReqVO;
import cn.iocoder.yudao.service.model.infra.logger.SystemOperateLog;
import cn.iocoder.yudao.service.model.infra.logger.SystemOperateLogFetcher;
import cn.iocoder.yudao.service.model.infra.logger.SystemOperateLogTable;
import cn.iocoder.yudao.service.model.system.user.SystemUserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;

public interface SystemOperateLogRepository extends JRepository<SystemOperateLog, Long> {
    SystemOperateLogTable systemOperatorLogTable = SystemOperateLogTable.$;

    default Page<SystemOperateLog> selectPage(OperateLogPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize())
                .execute(
                    sql()
                        .createQuery(systemOperatorLogTable)
                        .whereIf(reqVO.getType() != null, systemOperatorLogTable.type().eq(reqVO.getType()))
                        .whereIf(StringUtils.hasText(reqVO.getModule()), systemOperatorLogTable.module().eq(reqVO.getModule()))
                        .whereIf(StringUtils.hasText(reqVO.getUserNickname()), systemOperatorLogTable.user().nickname().eq(reqVO.getUserNickname()))
                        .whereIf(reqVO.getStartTime() != null, ()-> systemOperatorLogTable.startTime().between(reqVO.getStartTime()[0], reqVO.getStartTime()[1]))
                        .whereIf(reqVO.getSuccess() != null && reqVO.getSuccess(), systemOperatorLogTable.resultCode().eq(GlobalErrorCodeConstants.SUCCESS.getCode()))
                        .whereIf(reqVO.getSuccess() != null && !reqVO.getSuccess(), systemOperatorLogTable.resultCode().gt(GlobalErrorCodeConstants.SUCCESS.getCode()))
                        .select(systemOperatorLogTable.fetch(SystemOperateLogFetcher.$.allScalarFields().user(SystemUserFetcher.$.nickname())))
                );
    };

    default List<SystemOperateLog> selectList(OperateLogExportReqVO reqVO){
        return sql()
                .createQuery(systemOperatorLogTable)
                .whereIf(reqVO.getType() != null, systemOperatorLogTable.type().eq(reqVO.getType()))
                .whereIf(StringUtils.hasText(reqVO.getModule()), systemOperatorLogTable.module().eq(reqVO.getModule()))
                .whereIf(StringUtils.hasText(reqVO.getUserNickname()), systemOperatorLogTable.user().nickname().eq(reqVO.getUserNickname()))
                .whereIf(reqVO.getStartTime() != null, ()-> systemOperatorLogTable.startTime().between(reqVO.getStartTime()[0], reqVO.getStartTime()[1]))
                .whereIf(reqVO.getSuccess() != null && reqVO.getSuccess(), systemOperatorLogTable.resultCode().eq(GlobalErrorCodeConstants.SUCCESS.getCode()))
                .whereIf(reqVO.getSuccess() != null && !reqVO.getSuccess(), systemOperatorLogTable.resultCode().gt(GlobalErrorCodeConstants.SUCCESS.getCode()))
                .select(systemOperatorLogTable.fetch(SystemOperateLogFetcher.$.allScalarFields().user(SystemUserFetcher.$.nickname())))
                .execute();
    };
}
