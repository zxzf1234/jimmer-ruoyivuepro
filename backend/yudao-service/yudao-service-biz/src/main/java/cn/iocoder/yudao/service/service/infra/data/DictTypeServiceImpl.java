package cn.iocoder.yudao.service.service.infra.data;

import cn.iocoder.yudao.service.vo.infra.data.dictType.*;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.repository.infra.data.InfraDictDataRepository;
import cn.iocoder.yudao.service.model.infra.data.InfraDictType;
import cn.iocoder.yudao.service.repository.infra.data.InfraDictTypeRepository;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.*;

import cn.iocoder.yudao.service.convert.infra.data.DictTypeConvert;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.*;

/**
 * 字典类型 Service 实现类
 */
@Service
@Validated
public class DictTypeServiceImpl implements DictTypeService {

    @Resource
    InfraDictTypeRepository infraDictTypeRepository;

    @Resource
    InfraDictDataRepository infraDictDataRepository;


    @Override
    public UUID create(DictTypeCreateInput inputVO) {
        // 校验正确性
        validateDictTypeForCreateOrUpdate(null, inputVO.getName(), inputVO.getType());

        // 插入字典类型
        InfraDictType dictType = DictTypeConvert.INSTANCE.createInputConvert(inputVO);
        dictType = infraDictTypeRepository.insert(dictType);
        return dictType.id();
    }

    private void validateDictTypeForCreateOrUpdate(UUID id, String name, String type) {
        // 校验自己存在
        validateDictTypeExists(id);
        // 校验字典类型的名字的唯一性
        validateDictTypeNameUnique(id, name);
        // 校验字典类型的类型的唯一性
        validateDictTypeUnique(id, type);
    }

    void validateDictTypeNameUnique(UUID id, String name) {
        Optional<InfraDictType> opDictType = infraDictTypeRepository.findByName(name);
        if (!opDictType.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
        if (opDictType.get().id() != id) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
    }

    void validateDictTypeUnique(UUID id, String type) {
        if (StrUtil.isEmpty(type)) {
            return;
        }
        Optional<InfraDictType> opDictType = infraDictTypeRepository.findByType(type);
        if (!opDictType.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
        if (opDictType.get().id() != id) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
    }

    void validateDictTypeExists(UUID id) {
        if (id == null) {
            return;
        }
        Optional<InfraDictType> opDictType = infraDictTypeRepository.findById(id);
        if (!opDictType.isPresent()) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
    }

    @Override
    public Boolean update(DictTypeUpdateInput inputVO) {
        // 校验正确性
        validateDictTypeForCreateOrUpdate(inputVO.getId(), inputVO.getName(), null);

        // 更新字典类型
        InfraDictType updateObj = DictTypeConvert.INSTANCE.updateInputConvert(inputVO);
        infraDictTypeRepository.update(updateObj);
        return true;
    }

    @Override
    public Boolean delete(UUID id) {
        // 校验是否存在
        validateDictTypeExists(id);
        infraDictDataRepository.deleteByTypeId(id);

        // 删除字典类型
        infraDictTypeRepository.deleteById(id);
        return true;
    }

    @Override
    public PageResult<DictTypePageOutput> page(DictTypePageInput inputVO) {
        Page<InfraDictType> postPage = infraDictTypeRepository.selectPage(inputVO);
        List<DictTypePageOutput> postList =  DictTypeConvert.INSTANCE.pagePageOutputConvert(postPage);
        return new PageResult<>(postList, postPage.getTotalElements());
    }

    @Override
    public DictTypeGetOutput get(UUID id) {
        return infraDictTypeRepository.findById(id).map(DictTypeConvert.INSTANCE::getOutputConvert).orElse(null);
    }

    @Override
    public List<DictTypeListAllSimpleOutput> listAllSimple() {
        return DictTypeConvert.INSTANCE.listAllSimpleListOutputConvert(infraDictTypeRepository.findAll());
    }


    @Override
    public void export(HttpServletResponse response, DictTypeExportInput inputVO) {
        List<InfraDictType> list = infraDictTypeRepository.selectList(inputVO);
        List<DictTypeExcelOutput> data = DictTypeConvert.INSTANCE.listExportOutputConvert(list);
        try {
            ExcelUtils.write(response, "字典类型.xls", "类型列表", DictTypeExcelOutput.class, data);
        }
        catch (IOException e){
            throw exception(DICT_TYPE_EXPORT_EXCEPTION);
        }
    }

}
