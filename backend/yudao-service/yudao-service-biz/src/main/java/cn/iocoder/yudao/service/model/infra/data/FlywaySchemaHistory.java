package cn.iocoder.yudao.service.model.infra.data;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Entity
public interface FlywaySchemaHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int installedRank();

    @Nullable
    String version();

    String description();

    String type();

    String script();

    @Nullable
    String checksum();

    String installedBy();

    LocalDateTime installedOn();

    int executionTime();

    int success();
}
