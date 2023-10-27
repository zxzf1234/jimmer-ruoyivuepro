package cn.iocoder.yudao.service.service.infra.codegen;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.entity.EntityUtils;
import cn.iocoder.yudao.service.convert.infra.codegen.CodegenConvert;
import cn.iocoder.yudao.service.model.infra.codegen.*;
import cn.iocoder.yudao.service.repository.infra.codegen.*;
import cn.iocoder.yudao.service.service.infra.codegen.inner.CodegenEngine;
import cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule.*;
import org.babyfish.jimmer.meta.ImmutableProp;
import org.babyfish.jimmer.meta.ImmutableType;
import org.babyfish.jimmer.spring.repository.parser.PropPredicate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import org.babyfish.jimmer.ImmutableObjects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.*;

@Service
public class InterfaceServiceImpl implements InterfaceService{
    @Resource
    InfraInterfaceRepository infraInterfaceRepository;

    @Resource
    InfraInterfaceParamRepository infraInterfaceParamRepository;

    @Resource
    InfraInterfaceValidationRepository infraInterfaceValidationRepository;

    @Resource
    InfraInterfaceSubclassRepository infraInterfaceSubclassRepository;

    @Resource
    InfraInterfaceVoClassRepository infraInterfaceVoClassRepository;

    @Resource
    private CodegenEngine codegenEngine;

    @Override
    public PageResult<InterfaceResp> getList(InterfaceListReqVO reqVO){
        Page<InfraInterface> infraInterfaces = infraInterfaceRepository.getList(reqVO);
        List<InterfaceResp> interfaceRespList = CodegenConvert.INSTANCE.convertList12(infraInterfaces);
        return new PageResult<>(interfaceRespList, infraInterfaces.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(InterfaceEditInput reqVO){
        InfraInterface newInterface = CodegenConvert.INSTANCE.convert(reqVO);
        Optional<InfraInterface> opOldInterface = infraInterfaceRepository.findByNameAndModuleId(reqVO.getName(), reqVO.getModuleId());
        if(opOldInterface.isPresent())
            throw exception(CODEGEN_INTERFACE_EXITS);
        List<InfraInterfaceSubclass> newSubclasses = new ArrayList<>();
        List<InfraInterfaceValidation> newValidations = new ArrayList<>();
        List<InfraInterfaceParam> newParams = new ArrayList<>();
        for(InfraInterfaceSubclass inputSubclass : newInterface.inputSubclasses()){
            for(InfraInterfaceParam subclassParam : inputSubclass.subclassParams()){
                newValidations.addAll(subclassParam.validations());
                subclassParam = InfraInterfaceParamDraft.$.produce(subclassParam,
                        draft -> draft.setValidations(Collections.emptyList()));
                newParams.add(subclassParam);
            }
            inputSubclass = InfraInterfaceSubclassDraft.$.produce(inputSubclass,
                    draft -> draft.setSubclassParams(Collections.emptyList()));
            newSubclasses.add(inputSubclass);
        }
        for(InfraInterfaceSubclass outputSubclass : newInterface.outputSubclasses()){
            for(InfraInterfaceParam subclassParam : outputSubclass.subclassParams()){
                newValidations.addAll(subclassParam.validations());
                subclassParam = InfraInterfaceParamDraft.$.produce(subclassParam,
                        draft -> draft.setValidations(Collections.emptyList()));
                newParams.add(subclassParam);
            }
            outputSubclass = InfraInterfaceSubclassDraft.$.produce(outputSubclass,
                    draft -> draft.setSubclassParams(Collections.emptyList()));
            newSubclasses.add(outputSubclass);
        }
        for(InfraInterfaceParam inputParam : newInterface.inputParams()){
            newValidations.addAll(inputParam.validations());
            inputParam = InfraInterfaceParamDraft.$.produce(inputParam,
                    draft -> draft.setValidations(Collections.emptyList()));
            newParams.add(inputParam);
        }

        for(InfraInterfaceParam outputParam : newInterface.outputParams()){
            newValidations.addAll(outputParam.validations());
            outputParam = InfraInterfaceParamDraft.$.produce(outputParam,
                    draft -> draft.setValidations(Collections.emptyList()));
            newParams.add(outputParam);
        }

        newInterface = InfraInterfaceDraft.$.produce(newInterface,
                draft -> draft.setInputParams(Collections.emptyList())
                .setOutputParams(Collections.emptyList())
                .setInputSubclasses(Collections.emptyList())
                .setOutputSubclasses(Collections.emptyList()));
        newInterface = infraInterfaceRepository.insert(newInterface);
        infraInterfaceSubclassRepository.saveAll(newSubclasses);
        infraInterfaceParamRepository.saveAll(newParams);
        infraInterfaceValidationRepository.saveAll(newValidations);
        codegenEngine.interfaceInsert(newInterface.id());
        return newInterface.id().toString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(InterfaceEditInput reqVO){
        InfraInterface oldInterface = infraInterfaceRepository.findDetailById(reqVO.getId()).get();
        Optional<InfraInterface> opRepeatInterface = infraInterfaceRepository.findRepeat(reqVO.getName(), reqVO.getModuleId(), reqVO.getId());
        if (opRepeatInterface.isPresent())
            throw exception(CODEGEN_INTERFACE_EXITS);
        List<InfraInterfaceSubclass> newSubclasses = new ArrayList<>();
        List<InfraInterfaceValidation> newValidations = new ArrayList<>();
        List<InfraInterfaceParam> newParams = new ArrayList<>();
        List<UUID> deleteSubclasses = new ArrayList<>();
        List<UUID> deleteValidations = new ArrayList<>();
        List<UUID> deleteParams = new ArrayList<>();

        for(InterfaceEditInput.inputSubclass inputSubclass : reqVO.getInputSubclasses()){
            if(Objects.equals(inputSubclass.getOperateType(), "delete")){
                deleteSubclasses.add(inputSubclass.getId());
                for(InterfaceEditInput.subclassParam subclassParam : inputSubclass.getSubclassParams()){
                    deleteParams.add(subclassParam.getId());
                    deleteValidations.addAll(subclassParam.getValidations().stream()
                            .map(InterfaceEditInput.validation::getId).collect(Collectors.toList()));
                }
            }else if(Objects.equals(inputSubclass.getOperateType(), "new")){
                InfraInterfaceSubclass newSubclass = CodegenConvert.INSTANCE.convert(inputSubclass);

                for(InfraInterfaceParam subclassParam : newSubclass.subclassParams()){
                    newValidations.addAll(subclassParam.validations());
                    subclassParam = InfraInterfaceParamDraft.$.produce(subclassParam,
                            draft -> draft.setValidations(Collections.emptyList()));
                    newParams.add(subclassParam);
                }
                newSubclass = InfraInterfaceSubclassDraft.$.produce(newSubclass,
                        draft -> draft.setSubclassParams(Collections.emptyList()));
                newSubclasses.add(newSubclass);
            }else{
                InfraInterfaceSubclass updateSubclass = CodegenConvert.INSTANCE.convert(inputSubclass);
                updateSubclassParams(inputSubclass.getSubclassParams(), newParams, newValidations, deleteParams, deleteValidations);
                updateSubclass = InfraInterfaceSubclassDraft.$.produce(updateSubclass,
                        draft -> draft.setSubclassParams(Collections.emptyList()));
                Optional<InfraInterfaceSubclass> opOldSubclass = infraInterfaceSubclassRepository.findById(inputSubclass.getId());
                if(!opOldSubclass.isPresent())
                    throw exception(CODEGEN_INTERFACE_SUBCLASS_NOT_EXITS);
                if(!EntityUtils.isEquals(opOldSubclass.get(), updateSubclass))
                    infraInterfaceSubclassRepository.update(updateSubclass);
            }
        }

        for(InterfaceEditInput.outputSubclass outputSubclass : reqVO.getOutputSubclasses()){
            if(Objects.equals(outputSubclass.getOperateType(), "delete")){
                deleteSubclasses.add(outputSubclass.getId());
                for(InterfaceEditInput.subclassParam subclassParam : outputSubclass.getSubclassParams()){
                    deleteParams.add(subclassParam.getId());
                    deleteValidations.addAll(subclassParam.getValidations().stream()
                            .map(InterfaceEditInput.validation::getId).collect(Collectors.toList()));
                }
            }else if(Objects.equals(outputSubclass.getOperateType(), "new")){
                InfraInterfaceSubclass newSubclass = CodegenConvert.INSTANCE.convert(outputSubclass);

                for(InfraInterfaceParam subclassParam : newSubclass.subclassParams()){
                    newValidations.addAll(subclassParam.validations());
                    subclassParam = InfraInterfaceParamDraft.$.produce(subclassParam,
                            draft -> draft.setValidations(Collections.emptyList()));
                    newParams.add(subclassParam);
                }
                newSubclass = InfraInterfaceSubclassDraft.$.produce(newSubclass,
                        draft -> draft.setSubclassParams(Collections.emptyList()));
                newSubclasses.add(newSubclass);
            }else{
                InfraInterfaceSubclass updateSubclass = CodegenConvert.INSTANCE.convert(outputSubclass);
                updateSubclassParams(outputSubclass.getSubclassParams(), newParams, newValidations, deleteParams, deleteValidations);
                updateSubclass = InfraInterfaceSubclassDraft.$.produce(updateSubclass,
                        draft -> draft.setSubclassParams(Collections.emptyList()));
                Optional<InfraInterfaceSubclass> opOldSubclass = infraInterfaceSubclassRepository.findById(outputSubclass.getId());
                if(!opOldSubclass.isPresent())
                    throw exception(CODEGEN_INTERFACE_SUBCLASS_NOT_EXITS);
                if(!EntityUtils.isEquals(opOldSubclass.get(), updateSubclass))
                    infraInterfaceSubclassRepository.update(updateSubclass);
            }
        }
        for(InterfaceEditInput.inputParam inputParam : reqVO.getInputParams()){
            if(Objects.equals(inputParam.getOperateType(), "delete")){
                deleteParams.add(inputParam.getId());
                deleteValidations.addAll(inputParam.getValidations().stream().map(InterfaceEditInput.validation::getId)
                        .collect(Collectors.toList()));
            }else if(Objects.equals(inputParam.getOperateType(), "new")){
                InfraInterfaceParam newParam = CodegenConvert.INSTANCE.convert(inputParam);
                newValidations.addAll(newParam.validations());
                newParam = InfraInterfaceParamDraft.$.produce(newParam, draft -> draft.setValidations(Collections.emptyList()));
                newParams.add(newParam);
            }else {
                updateValidation(inputParam.getValidations(), newValidations, deleteValidations);
                Optional<InfraInterfaceParam> opOldParam = infraInterfaceParamRepository.findById(inputParam.getId());
                if(!opOldParam.isPresent()){
                    throw exception(CODEGEN_INTERFACE_PARAM_NOT_EXITS);
                }
                InfraInterfaceParam updateParam = CodegenConvert.INSTANCE.convert(inputParam);
                updateParam = InfraInterfaceParamDraft.$.produce(updateParam, draft -> draft.setValidations(Collections.emptyList()));
                if (!EntityUtils.isEquals(opOldParam.get(), updateParam))
                    infraInterfaceParamRepository.update(updateParam);
            }
        }

        for(InterfaceEditInput.outputParam outputParam : reqVO.getOutputParams()){
            if(Objects.equals(outputParam.getOperateType(), "delete")){
                deleteParams.add(outputParam.getId());
                deleteValidations.addAll(outputParam.getValidations().stream().map(InterfaceEditInput.validation::getId)
                        .collect(Collectors.toList()));
            }else if(Objects.equals(outputParam.getOperateType(), "new")){
                InfraInterfaceParam newParam = CodegenConvert.INSTANCE.convert(outputParam);
                newValidations.addAll(newParam.validations());
                newParam = InfraInterfaceParamDraft.$.produce(newParam, draft -> draft.setValidations(Collections.emptyList()));
                newParams.add(newParam);
            }else {
                updateValidation(outputParam.getValidations(), newValidations, deleteValidations);
                Optional<InfraInterfaceParam> opOldParam = infraInterfaceParamRepository.findById(outputParam.getId());
                if(!opOldParam.isPresent()){
                    throw exception(CODEGEN_INTERFACE_PARAM_NOT_EXITS);
                }
                InfraInterfaceParam updateParam = CodegenConvert.INSTANCE.convert(outputParam);
                updateParam = InfraInterfaceParamDraft.$.produce(updateParam, draft -> draft.setValidations(Collections.emptyList()));
                if (!EntityUtils.isEquals(opOldParam.get(), updateParam))
                    infraInterfaceParamRepository.update(updateParam);
            }
        }

        infraInterfaceSubclassRepository.saveAll(newSubclasses);
        infraInterfaceParamRepository.saveAll(newParams);
        infraInterfaceValidationRepository.saveAll(newValidations);

        if (!deleteValidations.isEmpty())
            infraInterfaceValidationRepository.deleteByIdIn(deleteValidations);
        if (!deleteParams.isEmpty())
            infraInterfaceParamRepository.deleteByIdIn(deleteParams);
        if (!deleteSubclasses.isEmpty())
            infraInterfaceSubclassRepository.deleteByIdIn(deleteSubclasses);

        InfraInterface updateInterface = CodegenConvert.INSTANCE.convert(reqVO);
        Optional<InfraInterface> opOldInterface = infraInterfaceRepository.findById(reqVO.getId());
        if (!opOldInterface.isPresent())
            throw exception(CODEGEN_INTERFACE_NOT_EXITS);
        updateInterface = InfraInterfaceDraft.$.produce(updateInterface,
                draft -> draft.setInputParams(Collections.emptyList())
                        .setOutputParams(Collections.emptyList())
                        .setInputSubclasses(Collections.emptyList())
                        .setOutputSubclasses(Collections.emptyList()));

        if(!EntityUtils.isEquals(opOldInterface.get(), updateInterface))
            infraInterfaceRepository.update(updateInterface);
        codegenEngine.interfaceUpdate(updateInterface.id(), oldInterface);
        return updateInterface.id().toString();
    }

    private void updateValidation(List<InterfaceEditInput.validation> validations, List<InfraInterfaceValidation> newValidations,
                                  List<UUID> deleteValidations){
        for(InterfaceEditInput.validation validation : validations){
            if(Objects.equals(validation.getOperateType(), "delete")){
                deleteValidations.add(validation.getId());
            }else if(Objects.equals(validation.getOperateType(), "new")){
                newValidations.add(CodegenConvert.INSTANCE.convert(validation));
            }else {
                InfraInterfaceValidation updateValidation = CodegenConvert.INSTANCE.convert(validation);
                Optional<InfraInterfaceValidation> opOldValidation = infraInterfaceValidationRepository.findById(validation.getId());
                if (!opOldValidation.isPresent())
                    throw exception(CODEGEN_INTERFACE_VALIDATION_NOT_EXITS);
                if(!EntityUtils.isEquals(opOldValidation.get(), updateValidation))
                    infraInterfaceValidationRepository.update(updateValidation);
            }
        }

    }

    private void updateSubclassParams(List<InterfaceEditInput.subclassParam> subclassParams,
                                      List<InfraInterfaceParam> newParams,
                                      List<InfraInterfaceValidation> newValidations,
                                      List<UUID> deleteParams,
                                      List<UUID> deleteValidations){
        for(InterfaceEditInput.subclassParam subclassParam : subclassParams){
            if(Objects.equals(subclassParam.getOperateType(), "delete")){
                deleteParams.add(subclassParam.getId());
                deleteValidations.addAll(subclassParam.getValidations().stream()
                        .map(InterfaceEditInput.validation::getId).collect(Collectors.toList()));
            }else if(Objects.equals(subclassParam.getOperateType(), "new")){
                InfraInterfaceParam newParam = CodegenConvert.INSTANCE.convert(subclassParam);
                newValidations.addAll(newParam.validations());
                newParam = InfraInterfaceParamDraft.$.produce(newParam, draft -> draft.setValidations(Collections.emptyList()));
                newParams.add(newParam);
            }else{
                InfraInterfaceParam updateParam = CodegenConvert.INSTANCE.convert(subclassParam);
                updateValidation(subclassParam.getValidations(), newValidations, deleteValidations);
                updateParam = InfraInterfaceParamDraft.$.produce(updateParam, draft -> draft.setValidations(Collections.emptyList()));
                Optional<InfraInterfaceParam> opOldParam = infraInterfaceParamRepository.findById(subclassParam.getId());
                if (!opOldParam.isPresent())
                    throw exception(CODEGEN_INTERFACE_PARAM_NOT_EXITS);
                if (!EntityUtils.isEquals(opOldParam, updateParam))
                    infraInterfaceParamRepository.update(updateParam);
            }
        }
    }

    @Override
    public InterfaceDetailResp getInterface(UUID id){
        Optional<InfraInterface> optionalInfraInterface = infraInterfaceRepository.findDetailById(id);
        if(!optionalInfraInterface.isPresent())
            throw exception(CODEGEN_INTERFACE_NOT_EXITS);
        return CodegenConvert.INSTANCE.convert1(optionalInfraInterface.get());
    }

    @Override
    public List<InterfaceVoClassOutput> getVoClassList(InterfaceListReqVO reqVO){
        List<InfraInterfaceVoClass> voClasses = infraInterfaceVoClassRepository.findList(reqVO);
        return CodegenConvert.INSTANCE.convertList18(voClasses);
    }
}
