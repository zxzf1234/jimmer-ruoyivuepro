package cn.iocoder.yudao.service.repository.infra.codegen;

import cn.iocoder.yudao.service.model.infra.codegen.*;
import cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule.InterfaceListReqVO;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InfraInterfaceRepository extends JRepository<InfraInterface, Long> {
    InfraInterfaceTable infraInterfaceTable = InfraInterfaceTable.$;

    default Page<InfraInterface> getList(InterfaceListReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(sql().createQuery(infraInterfaceTable)
                .whereIf(StringUtils.hasText(reqVO.getName()), infraInterfaceTable.name().like(reqVO.getName()))
                .whereIf(StringUtils.hasText(reqVO.getModuleName()), ()-> infraInterfaceTable.module().id().eq(UUID.fromString(reqVO.getModuleName())))
                .select(infraInterfaceTable.fetch(InfraInterfaceFetcher.$.allScalarFields().module(InfraInterfaceModuleFetcher.$.name())))
        );
    }
    default Optional<InfraInterface> findDetailById(UUID id){
        return sql().createQuery(infraInterfaceTable)
                .where(infraInterfaceTable.id().eq(id))
                .select(
                        infraInterfaceTable.fetch(InfraInterfaceFetcher.$.allTableFields()
                                .inputParams(InfraInterfaceParamFetcher.$.allTableFields()
                                        .validations(InfraInterfaceValidationFetcher.$.allTableFields()))
                                .outputParams(InfraInterfaceParamFetcher.$.allTableFields()
                                        .validations(InfraInterfaceValidationFetcher.$.allTableFields()))
                                .inputSubclasses(InfraInterfaceSubclassFetcher.$.allTableFields()
                                        .subclassParams(InfraInterfaceParamFetcher.$.allTableFields().
                                                validations(InfraInterfaceValidationFetcher.$.allTableFields())))
                                .outputSubclasses(InfraInterfaceSubclassFetcher.$.allTableFields()
                                        .subclassParams(InfraInterfaceParamFetcher.$.allTableFields().
                                                validations(InfraInterfaceValidationFetcher.$.allTableFields())))
                        )

                )
                .fetchOptional();
    };

    default Optional<InfraInterface> findRepeat(String name, UUID moduleId, UUID id){
        return sql().createQuery(infraInterfaceTable)
                .where(infraInterfaceTable.name().eq(name))
                .where(infraInterfaceTable.moduleId().eq(moduleId))
                .where(infraInterfaceTable.id().ne(id))
                .select(infraInterfaceTable)
                .fetchOptional();
    }

    Optional<InfraInterface> findById(UUID id);

    Optional<InfraInterface> findByNameAndModuleId(String name, UUID moduleId);


}