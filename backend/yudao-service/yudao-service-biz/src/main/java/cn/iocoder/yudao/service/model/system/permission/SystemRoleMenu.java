package cn.iocoder.yudao.service.model.system.permission;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;

import java.util.UUID;


@Entity
public interface SystemRoleMenu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    Long roleId();

    UUID menuId();

}