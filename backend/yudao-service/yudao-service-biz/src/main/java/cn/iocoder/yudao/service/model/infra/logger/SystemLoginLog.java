package cn.iocoder.yudao.service.model.infra.logger;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;

@Entity
public interface SystemLoginLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    long logType();

    String traceId();

    long userId();

    int userType();

    String username();

    int result();

    String userIp();

    String userAgent();

}
