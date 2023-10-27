package cn.iocoder.yudao.service.repository.infra.codegen;

import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceParam;
import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceParamTable;
import org.babyfish.jimmer.spring.repository.JRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfraInterfaceParamRepository extends JRepository<InfraInterfaceParam, Long> {
    InfraInterfaceParamTable infraInterfaceParamTable = InfraInterfaceParamTable.$;

    void deleteByIdIn(List<UUID> uuids);

    Optional<InfraInterfaceParam> findById(UUID id);

}