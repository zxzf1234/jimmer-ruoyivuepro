package cn.iocoder.yudao.service.model.infra.data;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Entity
public interface InfraApiErrorLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String traceId();

    long userId();

    int userType();

    String applicationName();

    String requestMethod();

    String requestUrl();

    String requestParams();

    String userIp();

    String userAgent();

    LocalDateTime exceptionTime();

    String exceptionName();

    @Nullable
    String exceptionMessage();

    @Nullable
    String exceptionRootCauseMessage();

    @Nullable
    String exceptionStackTrace();

    String exceptionClassName();

    String exceptionFileName();

    String exceptionMethodName();

    int exceptionLineNumber();

    int processStatus();

    @Nullable
    LocalDateTime processTime();

    long processUserId();
}
