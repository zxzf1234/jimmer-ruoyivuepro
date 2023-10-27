package cn.iocoder.yudao.service.model.infra.codegen;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;
import java.util.List;
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator;
import java.util.UUID;
@Entity
public interface InfraInterfaceParam extends BaseEntity {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    UUID id();

    @Key
    String name();

    String comment();

    Boolean isList();

    String variableType();

    String relatedId();

    Integer relatedType();

    String example();

    Boolean required();

    @Key
    UUID parentId();

    Integer parentType();

    @Key
    Integer inoutType();

    @Transient(InfraInterfaceValidationResolver.class)
    List<InfraInterfaceValidation> validations();

}