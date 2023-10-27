package cn.iocoder.yudao.service.repository.infra.codegen;

import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceSubclass;
import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceSubclassTable;
import org.babyfish.jimmer.spring.repository.JRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfraInterfaceSubclassRepository extends JRepository<InfraInterfaceSubclass, Long> {
    InfraInterfaceSubclassTable infraInterfaceSubclassTable = InfraInterfaceSubclassTable.$;

    void deleteByIdIn(List<UUID> ids);

    Optional<InfraInterfaceSubclass> findById(UUID id);

}