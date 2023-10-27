package cn.iocoder.yudao.service.model.system.dept;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import cn.iocoder.yudao.service.model.system.user.SystemUser;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;


@Entity
public interface SystemUserPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    @IdView
    Long userId();

    @ManyToOne
    @Nullable
    SystemUser user();

    Long postId();

}