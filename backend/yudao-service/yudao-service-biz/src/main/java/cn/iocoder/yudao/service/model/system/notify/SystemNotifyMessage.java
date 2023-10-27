package cn.iocoder.yudao.service.model.system.notify;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;
import java.time.LocalDateTime;
import java.util.Map;

import org.jetbrains.annotations.Nullable;


@Entity
public interface SystemNotifyMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    Long userId();

    Integer userType();

    Long templateId();

    String templateCode();

    String templateNickname();

    String templateContent();

    Integer templateType();

    @Serialized
    Map<String, Object> templateParams();

    Boolean readStatus();

    @Nullable
    LocalDateTime readTime();

}