package cn.iocoder.yudao.service.model.infra.file;

import cn.iocoder.yudao.framework.file.core.client.FileClientConfig;
import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;

@Entity
public interface InfraFileConfig extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String name();

    int storage();

    String remark();

    boolean master();

    FileClientConfig config();
}
