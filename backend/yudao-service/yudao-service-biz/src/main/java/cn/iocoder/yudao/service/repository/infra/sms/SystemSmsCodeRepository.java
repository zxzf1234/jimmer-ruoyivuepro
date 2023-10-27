package cn.iocoder.yudao.service.repository.infra.sms;

import cn.iocoder.yudao.service.model.infra.sms.SystemSmsCode;
import org.babyfish.jimmer.spring.repository.JRepository;

import java.util.Optional;

public interface SystemSmsCodeRepository extends JRepository<SystemSmsCode, Long> {

    Optional<SystemSmsCode> findByMobile(String mobile);

    Optional<SystemSmsCode> findByMobileAndCodeAndScene(String mobile, String code, Integer scene);
}
