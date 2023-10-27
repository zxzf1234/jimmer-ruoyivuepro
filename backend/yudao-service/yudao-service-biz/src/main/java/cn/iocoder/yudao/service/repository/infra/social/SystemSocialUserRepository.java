package cn.iocoder.yudao.service.repository.infra.social;

import cn.iocoder.yudao.service.model.infra.social.SystemSocialUser;
import org.babyfish.jimmer.spring.repository.JRepository;

import java.util.Optional;

public interface SystemSocialUserRepository extends JRepository<SystemSocialUser, Long> {

    Optional<SystemSocialUser> findByTypeAndCodeAndState(Integer type, String code, String state);

    Optional<SystemSocialUser> findByTypeAndOpenid(Integer type, String openId);


}
