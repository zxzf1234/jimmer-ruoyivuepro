package cn.iocoder.yudao.service.model.infra.sms;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
public interface SystemSmsLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    long channelId();

    String channelCode();

    long templateId();

    String templateCode();

    int templateType();

    String templateContent();

    @Serialized
    Map<String, Object> templateParams();

    String apiTemplateId();

    String mobile();

    long userId();

    int userType();

    int sendStatus();

    @Nullable
    LocalDateTime sendTime();

    int sendCode();

    String sendMsg();

    String apiSendCode();

    String apiSendMsg();

    String apiRequestId();

    String apiSerialNo();

    int receiveStatus();

    @Nullable
    LocalDateTime receiveTime();

    String apiReceiveCode();

    String apiReceiveMsg();

}
