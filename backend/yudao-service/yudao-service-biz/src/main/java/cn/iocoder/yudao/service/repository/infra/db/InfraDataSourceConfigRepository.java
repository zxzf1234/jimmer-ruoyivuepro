package cn.iocoder.yudao.service.repository.infra.db;

import cn.iocoder.yudao.service.model.infra.db.InfraDataSourceConfig;
import org.babyfish.jimmer.spring.repository.JRepository;

public interface InfraDataSourceConfigRepository extends JRepository<InfraDataSourceConfig, Long> {
}
