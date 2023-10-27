package cn.iocoder.yudao.service.repository.infra.data;

import cn.iocoder.yudao.service.model.infra.data.FlywaySchemaHistory;
import org.babyfish.jimmer.spring.repository.JRepository;

import java.util.List;
import java.util.Optional;

public interface FlywaySchemaHistoryRepository extends JRepository<FlywaySchemaHistory, Integer> {
     Optional<FlywaySchemaHistory> findFirstByVersionLikeOrderByInstalledRankDesc(String version);
}
