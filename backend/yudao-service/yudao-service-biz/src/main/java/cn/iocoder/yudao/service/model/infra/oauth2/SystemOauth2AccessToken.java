package cn.iocoder.yudao.service.model.infra.oauth2;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="system_oauth2_access_token")
public interface SystemOauth2AccessToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    long userId();

    int userType();

    String accessToken();

    String refreshToken();


    String clientId();

    @Nullable
    @Serialized
    List<String> scopes();

    LocalDateTime expiresTime();

}
