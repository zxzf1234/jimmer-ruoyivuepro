package cn.iocoder.yudao.service.convert.infra.codegen;

import cn.iocoder.yudao.service.model.infra.codegen.*;
import cn.iocoder.yudao.service.service.infra.codegen.inner.CodegenDatabaseColumn;
import cn.iocoder.yudao.service.service.infra.codegen.inner.CodegenDatabaseMapping;
import cn.iocoder.yudao.service.service.infra.codegen.inner.CodegenInterfaceParam;
import cn.iocoder.yudao.service.service.infra.codegen.inner.CodegenInterfaceSubclass;
import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.InterfaceValidationBase;
import cn.iocoder.yudao.service.vo.infra.codegen.database.*;
import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.DatabaseColumnBase;
import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.DatabaseIndexBase;
import cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper
public interface CodegenConvert {

    CodegenConvert INSTANCE = Mappers.getMapper(CodegenConvert.class);

    List<DatabaseTableResp> convertList05(List<InfraDatabaseTable> list);

    InfraDatabaseTable convert(DatabaseUpdateReq bean);

    InfraDatabaseColumn convert(DatabaseColumnBase bean);

    InfraDatabaseColumn convert(DatabaseUpdateReq.Column bean);

    InfraInterfaceValidation convert(DatabaseUpdateReq.Validation bean);

    List<InfraInterfaceValidation> convertList06(List<InterfaceValidationBase> validations);

    InfraDatabaseIndex convert(DatabaseIndexBase bean);

    InfraDatabaseIndex convert(DatabaseUpdateReq.Index bean);

    InfraDatabaseMapping convert(DatabaseUpdateReq.mapping bean);

    DatabaseTableDetailResp convert(InfraDatabaseTable bean);

    InfraInterfaceModule convert(InterfaceModuleCreateReq bean);

    InfraInterfaceModule convert(InterfaceModuleUpdateReq bean);

    InterfaceModuleResp convert(InfraInterfaceModule bean);

    InfraInterfaceParam convert(InterfaceEditInput.subclassParam bean);

    InfraInterfaceParam convert(InterfaceEditInput.inputParam bean);

    InfraInterfaceParam convert(InterfaceEditInput.outputParam bean);

    InfraInterfaceValidation convert(InterfaceEditInput.validation bean);

    InfraInterface convert(InterfaceEditInput bean);

    InfraInterface convert(InterfaceUpdateReq bean);

    InfraInterfaceSubclass convert(InterfaceEditInput.inputSubclass bean);

    InfraInterfaceSubclass convert(InterfaceEditInput.outputSubclass bean);

    @Mapping(source = "module.name", target = "moduleName")
    InterfaceResp convert(InfraInterface bean);

    InterfaceDetailResp convert1(InfraInterface bean);

    List<CodegenDatabaseColumn> convertList09(List<InfraDatabaseColumn> columns);

    List<InterfaceModuleResp> convertList10(List<InfraInterfaceModule> columns);

    List<InterfaceModuleSimpleRespVO> convertList11(List<InfraInterfaceModule> columns);

    List<InterfaceResp> convertList12(Page<InfraInterface> columns);

    List<DatabaseTableColumnResp> convertList13(List<InfraDatabaseTable> columns);

    List<InfraInterfaceParam> convertList14(List<InterfaceEditInput.inputParam> columns);

    List<InfraInterfaceParam> convertList15(List<InterfaceEditInput.outputParam> columns);

    List<CodegenDatabaseMapping> convertList16(List<InfraDatabaseMapping> columns);

    List<InfraInterfaceValidation> convertList17(List<DatabaseUpdateReq.Validation> validations);

    List<InterfaceVoClassOutput> convertList18(List<InfraInterfaceVoClass> validations);

    List<CodegenInterfaceParam> convertList19(List<InfraInterfaceParam> params);

    List<CodegenInterfaceSubclass> convertList20(List<InfraInterfaceSubclass> subclasses);


}
