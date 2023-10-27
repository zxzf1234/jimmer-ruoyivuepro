package cn.iocoder.yudao.service.repository.infra.codegen;

import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceModule;
import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceModuleTable;
import cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule.InterfaceModuleListReqVO;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfraInterfaceModuleRepository extends JRepository<InfraInterfaceModule, Long> {
    InfraInterfaceModuleTable infraInterfaceModuleTable = InfraInterfaceModuleTable.$;

    default List<InfraInterfaceModule> selectList(InterfaceModuleListReqVO reqVO){
        return sql().createQuery(infraInterfaceModuleTable)
                .whereIf(StringUtils.hasText(reqVO.getName()), infraInterfaceModuleTable.name().like(reqVO.getName()))
                .select(infraInterfaceModuleTable)
                .execute();
    }

    default List<InfraInterfaceModule> selectSimpleList(){
        return sql().createQuery(infraInterfaceModuleTable)
                .select(infraInterfaceModuleTable)
                .execute();
    }

    Optional<InfraInterfaceModule> findById(UUID id);

    void deleteById(UUID id);

    int countByParentId(String parentId);
}