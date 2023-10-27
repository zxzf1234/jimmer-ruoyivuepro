package cn.iocoder.yudao.service.model.system.dept;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;


@Entity
public interface SystemPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String code();

    String name();

    Integer sort();

    Integer status();

    @Nullable
    String remark();

}