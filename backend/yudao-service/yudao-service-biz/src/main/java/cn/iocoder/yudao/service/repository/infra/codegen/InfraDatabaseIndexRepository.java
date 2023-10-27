package cn.iocoder.yudao.service.repository.infra.codegen;

import cn.iocoder.yudao.service.model.infra.codegen.InfraDatabaseIndex;
import cn.iocoder.yudao.service.model.infra.codegen.InfraDatabaseIndexTable;
import org.babyfish.jimmer.spring.repository.JRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfraDatabaseIndexRepository extends JRepository<InfraDatabaseIndex, Long> {
    InfraDatabaseIndexTable infraDatabaseIndexTable = InfraDatabaseIndexTable.$;

    void deleteById(UUID id);

    Optional<InfraDatabaseIndex> findById(UUID id);
}