package cn.iocoder.yudao.service.model.infra.mail;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
public interface SystemMailLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    long userId();

    int userType();

    String toMail();

    long accountId();

    String fromMail();

    long templateId();

    String templateCode();

    String templateNickname();

    String templateTitle();

    String templateContent();

    @Serialized
    Map<String, Object> templateParams();

    int sendStatus();

    @Nullable
    LocalDateTime sendTime();

    @Nullable
    String sendMessageId();

    @Nullable
    String sendException();

}
