package cn.iocoder.yudao.service.repository.infra.logger;

import cn.iocoder.yudao.service.vo.infra.logger.loginlog.LoginLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.loginlog.LoginLogPageReqVO;
import cn.iocoder.yudao.service.enums.infra.logger.LoginResultEnum;
import cn.iocoder.yudao.service.model.infra.logger.SystemLoginLog;
import cn.iocoder.yudao.service.model.infra.logger.SystemLoginLogTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;

public interface SystemLoginLogRepository extends JRepository<SystemLoginLog, Long>{
    SystemLoginLogTable systemLoginLogTable = SystemLoginLogTable.$;

    default Page<SystemLoginLog> selectPage(LoginLogPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemLoginLogTable)
                        .whereIf(reqVO.getStatus() != null && Boolean.TRUE.equals(reqVO.getStatus()), systemLoginLogTable.result().eq(LoginResultEnum.SUCCESS.getResult()))
                        .whereIf(reqVO.getStatus() != null && !Boolean.TRUE.equals(reqVO.getStatus()), systemLoginLogTable.result().gt(LoginResultEnum.SUCCESS.getResult()))
                        .whereIf(StringUtils.hasText(reqVO.getUsername()), systemLoginLogTable.username().eq(reqVO.getUsername()))
                        .whereIf(StringUtils.hasText(reqVO.getUserIp()), systemLoginLogTable.userIp().eq(reqVO.getUserIp()))
                        .whereIf(reqVO.getCreateTime() != null, ()-> systemLoginLogTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(systemLoginLogTable)
        );
    }

    default List<SystemLoginLog> selectList(LoginLogExportReqVO reqVO){
        return sql()
                .createQuery(systemLoginLogTable)
                .whereIf(reqVO.getStatus() != null && Boolean.TRUE.equals(reqVO.getStatus()), systemLoginLogTable.result().eq(LoginResultEnum.SUCCESS.getResult()))
                .whereIf(reqVO.getStatus() != null && !Boolean.TRUE.equals(reqVO.getStatus()), systemLoginLogTable.result().gt(LoginResultEnum.SUCCESS.getResult()))
                .whereIf(StringUtils.hasText(reqVO.getUsername()), systemLoginLogTable.username().eq(reqVO.getUsername()))
                .whereIf(StringUtils.hasText(reqVO.getUserIp()), systemLoginLogTable.userIp().eq(reqVO.getUserIp()))
                .whereIf(reqVO.getCreateTime() != null, ()-> systemLoginLogTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                .select(systemLoginLogTable)
                .execute();
    }

}
