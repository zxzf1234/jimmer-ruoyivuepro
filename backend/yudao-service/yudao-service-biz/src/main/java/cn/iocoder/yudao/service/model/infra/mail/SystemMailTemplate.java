package cn.iocoder.yudao.service.model.infra.mail;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Entity
public interface SystemMailTemplate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String name();

    String code();

    long accountId();

    @Nullable
    String nickname();

    String title();

    String content();

    @Serialized
    List<String> params();

    int status();

    @Nullable
    String remark();

}
