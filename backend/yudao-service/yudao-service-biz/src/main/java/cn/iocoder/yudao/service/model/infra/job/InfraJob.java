package cn.iocoder.yudao.service.model.infra.job;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator;

import java.util.UUID;

@Entity
public interface InfraJob extends BaseEntity {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    UUID id();

    String name();

    int status();

    String handlerName();

    String handlerParam();

    String cronExpression();

    int retryCount();

    int retryInterval();

    int monitorTimeout();

}
