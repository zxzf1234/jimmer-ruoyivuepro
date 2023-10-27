package cn.iocoder.yudao.service.repository.infra.data;

import cn.iocoder.yudao.service.model.infra.data.*;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataExportInput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataPageInput;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfraDictDataRepository extends JRepository<InfraDictData, Long> {
    InfraDictDataTable infraDictDataTable = InfraDictDataTable.$;

    Optional<InfraDictData> findByTypeIdAndValue(UUID typeId, String value);

    Optional<InfraDictData> findById(UUID id);

    void deleteById(UUID id);

    default List<InfraDictData> findSimpleList(){
        return sql().createQuery(infraDictDataTable)
                .select(infraDictDataTable.fetch(InfraDictDataFetcher.$.allScalarFields().type(InfraDictTypeFetcher.$.type())))
                .execute();
    }

    default List<InfraDictData> selectList(DictDataExportInput reqVO){
        return sql()
                .createQuery(infraDictDataTable)
                .whereIf(StringUtils.hasText(reqVO.getLabel()),infraDictDataTable.label().like(reqVO.getLabel()))
                .whereIf(StringUtils.hasText(reqVO.getDictType()),infraDictDataTable.type().name().like(reqVO.getDictType()))
                .whereIf(reqVO.getStatus()!= null, infraDictDataTable.status().eq(reqVO.getStatus()))
                .orderBy(infraDictDataTable.type().name())
                .orderBy(infraDictDataTable.sort())
                .select(infraDictDataTable).execute();
    }

    default Page<InfraDictData> selectPage(DictDataPageInput reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(infraDictDataTable)
                        .whereIf(StringUtils.hasText(reqVO.getLabel()),infraDictDataTable.label().eq(reqVO.getLabel()))
                        .whereIf(StringUtils.hasText(reqVO.getDictType()),infraDictDataTable.type().type().like(reqVO.getDictType()))
                        .whereIf(reqVO.getStatus()!= null, infraDictDataTable.status().eq(reqVO.getStatus()))
                        .select(infraDictDataTable)
        );
    }

    void deleteByTypeId(UUID typeId);

    default Optional<InfraDictData> findByDictTypeAndValue(String dictType, String value){
        return sql()
                .createQuery(infraDictDataTable)
                .where(infraDictDataTable.type().type().eq(dictType))
                .where(infraDictDataTable.value().eq(value))
                .select(infraDictDataTable)
                .fetchOptional();
    }

    default Optional<InfraDictData> findByDictTypeAndLabel(String dictType, String label){
        return sql()
                .createQuery(infraDictDataTable)
                .where(infraDictDataTable.type().type().eq(dictType))
                .where(infraDictDataTable.label().eq(label))
                .select(infraDictDataTable)
                .fetchOptional();
    }

    default List<InfraDictData> findByDictTypeAndValueIn(String dictType, Collection<String> values){
        return sql()
                .createQuery(infraDictDataTable)
                .where(infraDictDataTable.type().type().eq(dictType))
                .where(infraDictDataTable.value().in(values))
                .select(infraDictDataTable)
                .execute();
    }
}