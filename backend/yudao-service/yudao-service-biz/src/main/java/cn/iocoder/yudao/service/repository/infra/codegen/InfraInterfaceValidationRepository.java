package cn.iocoder.yudao.service.repository.infra.codegen;

import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceValidation;
import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceValidationTable;
import org.babyfish.jimmer.spring.repository.JRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfraInterfaceValidationRepository extends JRepository<InfraInterfaceValidation, Long> {
    InfraInterfaceValidationTable infraInterfaceValidationTable = InfraInterfaceValidationTable.$;

    void deleteById(UUID id);

    void deleteByIdIn(List<UUID> uuids);

    Optional<InfraInterfaceValidation> findById(UUID id);
    List<InfraInterfaceValidation> findByParentId(UUID parentId);

}