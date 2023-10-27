package cn.iocoder.yudao.service.repository.infra.data;

import cn.iocoder.yudao.service.vo.infra.errorcode.ErrorCodeExportReqVO;
import cn.iocoder.yudao.service.vo.infra.errorcode.ErrorCodePageReqVO;
import cn.iocoder.yudao.service.model.infra.data.SystemErrorCode;
import cn.iocoder.yudao.service.model.infra.data.SystemErrorCodeTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SystemErrorCodeRepository extends JRepository<SystemErrorCode, Long> {
    SystemErrorCodeTable systemErrorCodeTable = SystemErrorCodeTable.$;

    default Page<SystemErrorCode> selectPage(ErrorCodePageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemErrorCodeTable)
                        .whereIf(StringUtils.hasText(reqVO.getApplicationName()),systemErrorCodeTable.applicationName().eq(reqVO.getApplicationName()))
                        .whereIf(StringUtils.hasText(reqVO.getMessage()),systemErrorCodeTable.message().eq(reqVO.getMessage()))
                        .whereIf(reqVO.getType() != null, systemErrorCodeTable.type().eq(reqVO.getType()))
                        .whereIf(reqVO.getCode() != null, systemErrorCodeTable.code().eq(reqVO.getCode()))
                        .whereIf(reqVO.getCreateTime() != null,  () -> systemErrorCodeTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(systemErrorCodeTable)
        );
    }

    default List<SystemErrorCode> selectList(ErrorCodeExportReqVO reqVO){
        return sql()
                .createQuery(systemErrorCodeTable)
                .whereIf(StringUtils.hasText(reqVO.getApplicationName()),systemErrorCodeTable.applicationName().eq(reqVO.getApplicationName()))
                .whereIf(StringUtils.hasText(reqVO.getMessage()),systemErrorCodeTable.message().eq(reqVO.getMessage()))
                .whereIf(reqVO.getType() != null, systemErrorCodeTable.type().eq(reqVO.getType()))
                .whereIf(reqVO.getCode() != null, systemErrorCodeTable.code().eq(reqVO.getCode()))
                .whereIf(reqVO.getCreateTime() != null,  () -> systemErrorCodeTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                .select(systemErrorCodeTable).execute();
    }

    Optional<SystemErrorCode> findByCode(Integer code);

    List<SystemErrorCode> findByCodeIn(List<Integer> codes);

    List<SystemErrorCode> findByApplicationNameAndUpdateTime(String applicationName, LocalDateTime minUpdateTime);
}
