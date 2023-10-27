package cn.iocoder.yudao.service.model.infra.sms;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;

@Entity
public interface SystemSmsChannel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String signature();

    String code();

    int status();

    String remark();

    String apiKey();

    String apiSecret();

    String callbackUrl();

}
