package cn.iocoder.yudao.service.service.infra.codegen;

import cn.iocoder.yudao.service.convert.infra.codegen.CodegenConvert;
import cn.iocoder.yudao.service.model.infra.codegen.InfraInterfaceModule;
import cn.iocoder.yudao.service.repository.infra.codegen.InfraInterfaceModuleRepository;
import cn.iocoder.yudao.service.service.infra.codegen.inner.CodegenEngine;
import cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.*;

@Service
public class InterfaceModuleServiceImpl implements InterfaceModuleService{

    @Resource
    InfraInterfaceModuleRepository infraInterfaceModuleRepository;

    @Resource
    private CodegenEngine codegenEngine;

    @Override
    public List<InterfaceModuleResp> getModuleList(InterfaceModuleListReqVO reqVO){
        List<InfraInterfaceModule> moduleList = infraInterfaceModuleRepository.selectList(reqVO);
        return CodegenConvert.INSTANCE.convertList10(moduleList);
    }

    @Override
    public List<InterfaceModuleSimpleRespVO> getSimpleModuleList(){
        List<InfraInterfaceModule> moduleList = infraInterfaceModuleRepository.selectSimpleList();
        return CodegenConvert.INSTANCE.convertList11(moduleList);
    }

    @Override
    public String create(InterfaceModuleCreateReq reqVO){
        InfraInterfaceModule module = CodegenConvert.INSTANCE.convert(reqVO);
        module = infraInterfaceModuleRepository.insert(module);
        if(module.type() == 1)
            codegenEngine.moduleInsertExecute(module);
        return module.id().toString();
    }

    @Override
    public String update(InterfaceModuleUpdateReq reqVO){
        Optional<InfraInterfaceModule> opModule = infraInterfaceModuleRepository.findById(reqVO.getId());
        if (!opModule.isPresent())
            throw exception(CODEGEN_INTERFACE_MODULE_NOT_EXITS);
        InfraInterfaceModule oldModule = opModule.get();
        InfraInterfaceModule newModule = infraInterfaceModuleRepository.update(CodegenConvert.INSTANCE.convert(reqVO));
        if(oldModule.type() == 1)
            codegenEngine.moduleUpdateExecute(oldModule, newModule);

        return newModule.id().toString();
    }

    @Override
    public InterfaceModuleResp getModule(String id){
        Optional<InfraInterfaceModule> opModule = infraInterfaceModuleRepository.findById(UUID.fromString(id));
        if (!opModule.isPresent())
            throw exception(CODEGEN_INTERFACE_MODULE_NOT_EXITS);
        InfraInterfaceModule module = opModule.get();
        return CodegenConvert.INSTANCE.convert(module);
    }

    @Override
    public void deleteModule(String id){
        int count = infraInterfaceModuleRepository.countByParentId(id);
        if(count > 0)
            throw exception(CODEGEN_INTERFACE_MODULE_CHILD_NODE_EXITS);
        Optional<InfraInterfaceModule> opModule = infraInterfaceModuleRepository.findById(UUID.fromString(id));
        if (!opModule.isPresent())
            throw exception(CODEGEN_INTERFACE_MODULE_NOT_EXITS);
        InfraInterfaceModule module = opModule.get();
        if(module.type() == 1)
            codegenEngine.moduleDeleteExecute(module);
        infraInterfaceModuleRepository.deleteById(UUID.fromString(id));
    }
}
