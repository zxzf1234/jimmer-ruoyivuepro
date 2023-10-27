package cn.iocoder.yudao.service.repository.infra.job;

import cn.iocoder.yudao.service.vo.infra.job.job.JobExportReqVO;
import cn.iocoder.yudao.service.vo.infra.job.job.JobPageReqVO;
import cn.iocoder.yudao.service.model.infra.job.InfraJob;
import cn.iocoder.yudao.service.model.infra.job.InfraJobTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfraJobRepository extends JRepository<InfraJob, Long> {
    InfraJobTable infraJobTable = InfraJobTable.$;

    default Page<InfraJob> selectPage(JobPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(infraJobTable)
                        .whereIf(reqVO.getStatus() != null, infraJobTable.status().eq(reqVO.getStatus()))
                        .whereIf(StringUtils.hasText(reqVO.getHandlerName()), infraJobTable.handlerName().eq(reqVO.getHandlerName()))
                        .whereIf(StringUtils.hasText(reqVO.getName()), infraJobTable.name().eq(reqVO.getName()))
                        .select(infraJobTable)
        );
    }

    default List<InfraJob> selectList(JobExportReqVO reqVO){
        return sql()
                .createQuery(infraJobTable)
                .whereIf(reqVO.getStatus() != null, infraJobTable.status().eq(reqVO.getStatus()))
                .whereIf(StringUtils.hasText(reqVO.getHandlerName()), infraJobTable.handlerName().eq(reqVO.getHandlerName()))
                .whereIf(StringUtils.hasText(reqVO.getName()), infraJobTable.name().eq(reqVO.getName()))
                .select(infraJobTable)
                .execute();

    }

    Optional<InfraJob> findById(UUID id);

    void deleteById(UUID id);

    Optional<InfraJob> findByHandlerName(String handlerName);

    List<InfraJob> findByIdIn(Collection<UUID> ids);
}
