package cn.iocoder.yudao.service.model.infra.data;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import cn.iocoder.yudao.service.model.infra.data.InfraDictType;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator;
import java.util.UUID;
@Entity
public interface InfraDictData extends BaseEntity {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    UUID id();

    Integer sort();

    String label();

    @Key
    String value();

    @IdView
    @Nullable
    UUID typeId();

    @Key
    @ManyToOne
    @Nullable
    InfraDictType type();

    Integer status();

    String colorType();

    String cssClass();

    String remark();

}