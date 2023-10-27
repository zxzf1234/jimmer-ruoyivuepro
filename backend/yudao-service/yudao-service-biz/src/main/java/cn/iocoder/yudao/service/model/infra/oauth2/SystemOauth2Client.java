package cn.iocoder.yudao.service.model.infra.oauth2;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Entity
@Table(name="system_oauth2_client")
public interface SystemOauth2Client extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    String clientId();

    String secret();

    String name();

    @Nullable
    String logo();

    @Nullable
    String description();

    int status();

    int accessTokenValiditySeconds();

    int refreshTokenValiditySeconds();

    @Serialized
    List<String> redirectUris();

    @Serialized
    List<String> authorizedGrantTypes();

    @Nullable
    @Serialized
    List<String> scopes();

    @Nullable
    @Serialized
    List<String> autoApproveScopes();

    @Nullable
    @Serialized
    List<String> authorities();

    @Nullable
    @Serialized
    List<String> resourceIds();

    @Nullable
    String additionalInformation();

}
