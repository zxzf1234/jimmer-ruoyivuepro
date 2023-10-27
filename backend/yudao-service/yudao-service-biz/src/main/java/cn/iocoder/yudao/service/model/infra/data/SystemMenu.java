package cn.iocoder.yudao.service.model.infra.data;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.meta.UUIDIdGenerator;

import java.util.UUID;

@Entity
public interface SystemMenu extends BaseEntity {
    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    UUID id();

    String name();

    String permission();

    int type();

    int sort();

    String parentId();

    String path();

    String icon();

    String component();

    String componentName();

    int status();

    boolean visible();

    boolean keepAlive();

    boolean alwaysShow();

}
