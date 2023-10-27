package cn.iocoder.yudao.service.model.infra.sms;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;

import java.util.List;

@Entity
public interface SystemSmsTemplate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    int type();

    int status();

    String code();

    String name();

    String content();

    @Serialized
    List<String> params();

    String remark();

    String apiTemplateId();

    long channelId();

    String channelCode();

}
