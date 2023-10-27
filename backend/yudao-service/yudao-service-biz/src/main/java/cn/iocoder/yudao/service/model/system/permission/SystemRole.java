package cn.iocoder.yudao.service.model.system.permission;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;
import java.util.List;




@Entity
public interface SystemRole extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String name();

    String code();

    Integer sort();

    Integer dataScope();

    @Serialized
    List<Long> dataScopeDeptIds();

    Integer status();

    Integer type();

    String remark();

}