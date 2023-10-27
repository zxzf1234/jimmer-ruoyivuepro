package cn.iocoder.yudao.service.model.infra.oauth2;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;

import java.time.LocalDateTime;

@Entity
@Table(name="system_oauth2_approve")
public interface SystemOauth2Approve extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    long userId();

    int userType();

    String clientId();

    String scope();

    boolean approved();

    LocalDateTime expiresTime();

}
