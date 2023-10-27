package cn.iocoder.yudao.service.model.infra.codegen;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;


import org.babyfish.jimmer.sql.meta.UUIDIdGenerator;
import java.util.UUID;


@Entity
public interface InfraInterfaceModule extends BaseEntity {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    UUID id();

    String name();

    String comment();

    String parentId();

    Integer type();

}