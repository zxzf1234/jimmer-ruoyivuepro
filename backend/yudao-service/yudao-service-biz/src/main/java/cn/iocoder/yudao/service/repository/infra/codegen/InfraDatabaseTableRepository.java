package cn.iocoder.yudao.service.repository.infra.codegen;

import cn.iocoder.yudao.service.model.infra.codegen.*;
import cn.iocoder.yudao.service.vo.infra.codegen.database.DatabaseTableListReqVO;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfraDatabaseTableRepository extends JRepository<InfraDatabaseTable, Long> {
    InfraDatabaseTableTable infraDatabaseTableTable = InfraDatabaseTableTable.$;

    default List<InfraDatabaseTable> selectList(DatabaseTableListReqVO listReqVO){
        return sql().createQuery(infraDatabaseTableTable)
                .whereIf(StringUtils.hasText(listReqVO.getTableComment()), infraDatabaseTableTable.comment().like(listReqVO.getTableComment()))
                .whereIf(StringUtils.hasText(listReqVO.getTableName()), infraDatabaseTableTable.name().like(listReqVO.getTableName()))
                .orderBy(infraDatabaseTableTable.businessName())
                .orderBy(infraDatabaseTableTable.name())
                .select(infraDatabaseTableTable)
                .execute();

    }

    default List<InfraDatabaseTable> selectColumnList(DatabaseTableListReqVO listReqVO){
        return sql().createQuery(infraDatabaseTableTable)
                .whereIf(StringUtils.hasText(listReqVO.getTableComment()), infraDatabaseTableTable.comment().like(listReqVO.getTableComment()))
                .whereIf(StringUtils.hasText(listReqVO.getTableName()), infraDatabaseTableTable.name().like(listReqVO.getTableName()))
                .select(infraDatabaseTableTable.fetch(InfraDatabaseTableFetcher.$.allScalarFields()
                        .columns(InfraDatabaseColumnFetcher.$.allTableFields())))
                .execute();

    }

    default Optional<InfraDatabaseTable> findDetailById(UUID tableId){
        return sql().createQuery(infraDatabaseTableTable)
                .where(infraDatabaseTableTable.id().eq(tableId))
                .select(infraDatabaseTableTable.fetch(InfraDatabaseTableFetcher.$.allScalarFields()
                        .columns(InfraDatabaseColumnFetcher.$.allTableFields().validations(InfraInterfaceValidationFetcher.$.allTableFields()))
                        .indexes(InfraDatabaseIndexFetcher.$.allTableFields())
                        .mappings(InfraDatabaseMappingFetcher.$.allTableFields())
                ))
                .fetchOptional();
    }

    default void updateById(UUID id, InfraDatabaseTable newTable){
        sql().createUpdate(infraDatabaseTableTable)
                .set(infraDatabaseTableTable.name(), newTable.name())
                .set(infraDatabaseTableTable.businessName(), newTable.businessName())
                .set(infraDatabaseTableTable.comment(), newTable.comment())
                .set(infraDatabaseTableTable.remark(), newTable.remark())
                .where(infraDatabaseTableTable.id().eq(id))
                .execute();
    }

    Optional<InfraDatabaseTable> findById(UUID id);

    Optional<InfraDatabaseTable> findByName(String name);
}
