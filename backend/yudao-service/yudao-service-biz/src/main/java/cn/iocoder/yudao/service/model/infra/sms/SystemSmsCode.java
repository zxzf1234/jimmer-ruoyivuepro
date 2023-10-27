package cn.iocoder.yudao.service.model.infra.sms;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Entity
public interface SystemSmsCode extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String mobile();

    String code();

    String createIp();

    int scene();

    int todayIndex();

    boolean used();

    @Nullable
    LocalDateTime usedTime();

    String usedIp();

}
