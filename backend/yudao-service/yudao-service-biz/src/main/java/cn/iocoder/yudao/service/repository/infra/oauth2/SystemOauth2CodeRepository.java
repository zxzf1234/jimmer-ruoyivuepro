package cn.iocoder.yudao.service.repository.infra.oauth2;

import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2Code;
import org.babyfish.jimmer.spring.repository.JRepository;

import java.util.Optional;

public interface SystemOauth2CodeRepository extends JRepository<SystemOauth2Code, Long> {
    Optional<SystemOauth2Code> findByCode(String code);
}
