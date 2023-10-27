package cn.iocoder.yudao.service.repository.infra.data;

import cn.iocoder.yudao.service.model.infra.data.InfraDictType;
import cn.iocoder.yudao.service.model.infra.data.InfraDictTypeTable;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypeExportInput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypePageInput;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfraDictTypeRepository extends JRepository<InfraDictType, Long> {
    InfraDictTypeTable infraDictTypeTable = InfraDictTypeTable.$;

    Optional<InfraDictType> findById(UUID id);

    Optional<InfraDictType> findByName(String name);

    Optional<InfraDictType> findByType(String type);

    void deleteById(UUID id);

    default Page<InfraDictType> selectPage(DictTypePageInput reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(infraDictTypeTable)
                        .whereIf(StringUtils.hasText(reqVO.getName()),infraDictTypeTable.name().like(reqVO.getName()))
                        .whereIf(StringUtils.hasText(reqVO.getType()),infraDictTypeTable.type().like(reqVO.getType()))
                        .whereIf(reqVO.getCreateTime() != null,  () -> infraDictTypeTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(infraDictTypeTable)
        );
    }

    default List<InfraDictType> selectList(DictTypeExportInput reqVO){
        return sql()
                .createQuery(infraDictTypeTable)
                .whereIf(StringUtils.hasText(reqVO.getName()),infraDictTypeTable.name().like(reqVO.getName()))
                .whereIf(StringUtils.hasText(reqVO.getType()),infraDictTypeTable.type().like(reqVO.getType()))
                .whereIf(reqVO.getCreateTime() != null,  () -> infraDictTypeTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                .select(infraDictTypeTable).execute();
    }
}