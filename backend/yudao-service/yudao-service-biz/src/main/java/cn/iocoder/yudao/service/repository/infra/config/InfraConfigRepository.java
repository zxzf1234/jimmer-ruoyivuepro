package cn.iocoder.yudao.service.repository.infra.config;

import cn.iocoder.yudao.service.vo.infra.config.ConfigExportReqVO;
import cn.iocoder.yudao.service.vo.infra.config.ConfigPageReqVO;
import cn.iocoder.yudao.service.model.infra.config.InfraConfig;
import cn.iocoder.yudao.service.model.infra.config.InfraConfigTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public interface InfraConfigRepository extends JRepository<InfraConfig, Long> {
    InfraConfigTable infraConfigTable = InfraConfigTable.$;

    default List<InfraConfig> selectList(ConfigExportReqVO reqVO){
        return sql()
                .createQuery(infraConfigTable)
                .whereIf(reqVO.getType() != null, infraConfigTable.type().eq(reqVO.getType()))
                .whereIf(StringUtils.hasText(reqVO.getKey()), infraConfigTable.configKey().eq(reqVO.getKey()))
                .whereIf(StringUtils.hasText(reqVO.getName()), infraConfigTable.name().eq(reqVO.getName()))
                .whereIf(reqVO.getCreateTime()!=null, ()-> infraConfigTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                .select(infraConfigTable)
                .execute();
    }

    default Page<InfraConfig> selectPage(ConfigPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql().createQuery(infraConfigTable)
                        .whereIf(reqVO.getType() != null, infraConfigTable.type().eq(reqVO.getType()))
                        .whereIf(StringUtils.hasText(reqVO.getKey()), infraConfigTable.configKey().eq(reqVO.getKey()))
                        .whereIf(StringUtils.hasText(reqVO.getName()), infraConfigTable.name().eq(reqVO.getName()))
                        .whereIf(reqVO.getCreateTime()!=null, ()-> infraConfigTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(infraConfigTable)
        );
    }

    Optional<InfraConfig> findByConfigKey(String key);
}
