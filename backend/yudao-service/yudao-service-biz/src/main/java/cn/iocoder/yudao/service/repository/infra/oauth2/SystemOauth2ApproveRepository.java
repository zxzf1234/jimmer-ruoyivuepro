package cn.iocoder.yudao.service.repository.infra.oauth2;

import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2Approve;
import org.babyfish.jimmer.spring.repository.JRepository;

import java.util.List;

public interface SystemOauth2ApproveRepository extends JRepository<SystemOauth2Approve, Long> {
    default void upsert(SystemOauth2Approve approveDO){
        sql()
                .getEntities()
                .save(approveDO);
    }

    List<SystemOauth2Approve> findByUserIdAndUserTypeAndClientId(Long userId, Integer userType, String clientId);
}
