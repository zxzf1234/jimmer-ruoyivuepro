package cn.iocoder.yudao.service.model.infra.codegen;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceModule;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator;
import java.util.UUID;
@Entity
public interface InfraInterface extends BaseEntity {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    UUID id();

    String name();

    String comment();

    String method();

    String authorize();

    Boolean isTransaction();

    @IdView
    @Nullable
    UUID moduleId();

    @ManyToOne
    @Nullable
    InfraInterfaceModule module();

    String inputType();

    String inputExtendClass();

    String outputType();

    String outputExtendClass();

    Boolean inputServlet();

    @Transient(InfraInterfaceInputParamResolver.class)
    List<InfraInterfaceParam> inputParams();

    @Transient(InfraInterfaceInputSubclassResolver.class)
    List<InfraInterfaceSubclass> inputSubclasses();

    @Transient(InfraInterfaceOutputParamResolver.class)
    List<InfraInterfaceParam> outputParams();

    @Transient(InfraInterfaceOutputSubclassResolver.class)
    List<InfraInterfaceSubclass> outputSubclasses();

}