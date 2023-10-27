package cn.iocoder.yudao.service.model.system.dept;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import cn.iocoder.yudao.service.model.system.user.SystemUser;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;



@Entity
public interface SystemDept extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String name();

    Long parentId();

    Integer sort();

    @IdView
    Long leaderUserId();

    @ManyToOne
    @Nullable
    SystemUser leaderUser();

    String phone();

    String email();

    Integer status();

}