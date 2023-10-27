package cn.iocoder.yudao.service.repository.infra.social;

import cn.iocoder.yudao.service.model.infra.social.SystemSocialUserBind;
import org.babyfish.jimmer.spring.repository.JRepository;

import java.util.List;
import java.util.Optional;

public interface SystemSocialUserBindRepository extends JRepository<SystemSocialUserBind, Long> {
    List<SystemSocialUserBind> findByUserIdAndUserType(Long userId, Integer userType);

    void deleteByUserTypeAndSocialUserId(Integer userType, Long userId);

    void deleteByUserTypeAndUserIdAndSocialType(Integer userType, Long userId, Integer socialType);

    Optional<SystemSocialUserBind> findByUserTypeAndSocialUserId(Integer userType, Long socialUserId);
}
