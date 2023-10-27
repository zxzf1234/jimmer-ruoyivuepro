package cn.iocoder.yudao.service.model.infra.codegen;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public interface InfraDatabaseTable extends BaseEntity {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    UUID id();

    @Key
    String name();

    String comment();

    String remark();

    String businessName();

    @OneToMany(mappedBy = "table", orderedProps =@OrderedProp("sort"))
    List<InfraDatabaseColumn> columns();

    @OneToMany(mappedBy = "table")
    List<InfraDatabaseIndex> indexes();

    @OneToMany(mappedBy = "table")
    List<InfraDatabaseMapping> mappings();
}
