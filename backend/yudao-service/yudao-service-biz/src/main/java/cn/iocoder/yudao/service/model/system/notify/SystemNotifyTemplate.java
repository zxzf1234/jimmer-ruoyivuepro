package cn.iocoder.yudao.service.model.system.notify;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;
import java.util.List;


@Entity
public interface SystemNotifyTemplate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String name();

    String code();

    String nickname();

    String content();

    Integer type();

    @Serialized
    List<String> params();

    Integer status();

    String remark();

}