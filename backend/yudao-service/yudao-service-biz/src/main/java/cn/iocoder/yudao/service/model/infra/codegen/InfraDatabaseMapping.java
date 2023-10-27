package cn.iocoder.yudao.service.model.infra.codegen;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import cn.iocoder.yudao.service.model.infra.codegen.InfraDatabaseTable;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator;
import java.util.UUID;
@Entity
public interface InfraDatabaseMapping extends BaseEntity {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    UUID id();

    @IdView
    @Nullable
    UUID tableId();

    @ManyToOne
    @Nullable
    @Key
    InfraDatabaseTable table();

    @Key
    String name();

    Boolean isList();

    String annotate();

    String mappingTable();

}