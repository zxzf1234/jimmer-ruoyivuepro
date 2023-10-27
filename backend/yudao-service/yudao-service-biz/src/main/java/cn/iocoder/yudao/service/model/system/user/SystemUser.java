package cn.iocoder.yudao.service.model.system.user;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import cn.iocoder.yudao.service.model.system.dept.SystemDept;
import org.babyfish.jimmer.sql.*;
import java.time.LocalDateTime;
import org.jetbrains.annotations.Nullable;
import java.util.List;


@Entity
public interface SystemUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String username();

    String password();

    String nickname();

    @Nullable
    String remark();

    @IdView
    Long deptId();

    @ManyToOne
    @Nullable
    SystemDept dept();

    @Nullable
    @Serialized
    List<Long> postIds();

    String email();

    String mobile();

    Integer sex();

    String avatar();

    Integer status();

    String loginIp();

    @Nullable
    LocalDateTime loginDate();

}