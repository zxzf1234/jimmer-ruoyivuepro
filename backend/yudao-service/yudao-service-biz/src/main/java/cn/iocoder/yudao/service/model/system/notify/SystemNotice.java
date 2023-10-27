package cn.iocoder.yudao.service.model.system.notify;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;


@Entity
public interface SystemNotice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String title();

    String content();

    Integer type();

    Integer status();

}