package cn.iocoder.yudao.service.model.infra.logger;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import cn.iocoder.yudao.service.model.system.user.SystemUser;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
public interface SystemOperateLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String traceId();

    @IdView
    Long userId();

    int userType();

    String module();

    String name();

    int type();

    String content();

    @Serialized
    Map<String, Object> exts();

    String requestMethod();

    String requestUrl();

    String userIp();

    String userAgent();

    String javaMethod();

    String javaMethodArgs();

    @Nullable
    LocalDateTime startTime();

    int duration();

    int resultCode();

    String resultMsg();

    String resultData();

    @ManyToOne
    @Nullable
    SystemUser user();
}
