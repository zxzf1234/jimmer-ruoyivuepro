package cn.iocoder.yudao.service.model.infra.social;

import cn.iocoder.yudao.service.model.base.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;

@Entity
public interface SystemSocialUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id();

    int type();

    String openid();

    String token();

    String rawTokenInfo();

    String nickname();

    String avatar();

    String rawUserInfo();

    String code();

    String state();

}
