package cn.iocoder.yudao.service.model.infra.codegen;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import cn.iocoder.yudao.service.model.infra.codegen.InfraInterface;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator;
import java.util.UUID;
@Entity
public interface InfraInterfaceSubclass extends BaseEntity {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    UUID id();

    @IdView
    @Nullable
    UUID parentId();

    @Key
    @ManyToOne
    @Nullable
    InfraInterface parent();

    @Key
    String name();

    String comment();

    String inheritClass();

    Integer inheritType();

    @Key
    Integer type();

    @Transient(InfraInterfaceSubclassParamResolver.class)
    List<InfraInterfaceParam> subclassParams();

}