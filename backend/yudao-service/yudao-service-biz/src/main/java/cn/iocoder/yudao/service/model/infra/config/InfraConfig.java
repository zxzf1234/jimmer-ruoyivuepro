package cn.iocoder.yudao.service.model.infra.config;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;

@Entity
public interface InfraConfig extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String category();

    int type();

    String name();

    String configKey();

    String value();

    boolean visible();

    String remark();
}
