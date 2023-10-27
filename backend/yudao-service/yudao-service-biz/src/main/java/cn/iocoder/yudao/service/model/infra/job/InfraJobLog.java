package cn.iocoder.yudao.service.model.infra.job;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Entity
public interface InfraJobLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String jobId();

    String handlerName();

    String handlerParam();

    int executeIndex();

    LocalDateTime beginTime();

    @Nullable
    LocalDateTime endTime();

    int duration();

    int status();

    String result();
}
