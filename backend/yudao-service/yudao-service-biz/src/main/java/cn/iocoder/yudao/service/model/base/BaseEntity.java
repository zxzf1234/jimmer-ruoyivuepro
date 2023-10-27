package cn.iocoder.yudao.service.model.base;

import cn.iocoder.yudao.service.model.system.user.SystemUser;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.MappedSuperclass;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@MappedSuperclass
public interface BaseEntity {
    LocalDateTime createTime();

    LocalDateTime updateTime();

    @ManyToOne
    @Nullable
    SystemUser creator();

    @ManyToOne
    @Nullable
    SystemUser updater();

    @IdView
    Long creatorId();

    @IdView
    Long updaterId();

    boolean deleted();
}
