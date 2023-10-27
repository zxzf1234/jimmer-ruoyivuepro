package cn.iocoder.yudao.service.service.infra.data;

import cn.iocoder.yudao.service.api.system.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.service.model.infra.data.*;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataCreateInput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataUpdateInput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataGetOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataPageOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataPageInput;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.service.vo.infra.data.dictData.*;
import cn.iocoder.yudao.service.repository.infra.data.InfraDictDataRepository;
import cn.iocoder.yudao.service.repository.infra.data.InfraDictTypeRepository;
import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.service.convert.infra.data.DictDataConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.*;

/**
 * 字典数据 Service 实现类
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class DictDataServiceImpl implements DictDataService {


    @Resource
    private InfraDictDataRepository infraDictDataRepository;

    @Resource
    private InfraDictTypeRepository infraDictTypeRepository;


    public InfraDictType validateDictTypeExists(String type) {
        Optional<InfraDictType> opDictType = infraDictTypeRepository.findByType(type);
        if (!opDictType.isPresent()) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        if (!CommonStatusEnum.ENABLE.getStatus().equals(opDictType.get().status())) {
            throw exception(DICT_TYPE_NOT_ENABLE);
        }
        return opDictType.get();
    }

    public void validateDictTypeExists(UUID typeId) {
        Optional<InfraDictType> opDictType = infraDictTypeRepository.findById(typeId);
        if (!opDictType.isPresent()) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        if (!CommonStatusEnum.ENABLE.getStatus().equals(opDictType.get().status())) {
            throw exception(DICT_TYPE_NOT_ENABLE);
        }
    }

    public void validateDictDataValueUnique(UUID id, String type, String value) {
        Optional<InfraDictType> opDictType = infraDictTypeRepository.findByType(type);
        if(!opDictType.isPresent()){
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        Optional<InfraDictData> opDictData = infraDictDataRepository.findByTypeIdAndValue(opDictType.get().id(), value);
        if (!opDictData.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
        if (id == null) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
        if (opDictData.get().id() != id) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
    }

    public void validateDictDataValueUnique(UUID id, UUID typeId, String value) {
        Optional<InfraDictData> opDictData = infraDictDataRepository.findByTypeIdAndValue(typeId, value);
        if (!opDictData.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
        if (id == null) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
        if (!opDictData.get().id().equals(id)) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
    }

    public void validateDictDataExists(UUID id) {
        if (id == null) {
            return;
        }
        Optional<InfraDictData> opDictData = infraDictDataRepository.findById(id);
        if (!opDictData.isPresent()) {
            throw exception(DICT_DATA_NOT_EXISTS);
        }
    }

    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        if (CollUtil.isEmpty(values)) {
            return;
        }
        Map<String, InfraDictData> dictDataMap = CollectionUtils.convertMap(
                infraDictDataRepository.findByDictTypeAndValueIn(dictType, values), InfraDictData::value);
        // 校验
        values.forEach(value -> {
            InfraDictData dictData = dictDataMap.get(value);
            if (dictData == null) {
                throw exception(DICT_DATA_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dictData.status())) {
                throw exception(DICT_DATA_NOT_ENABLE, dictData.label());
            }
        });
    }

    @Override
    public DictDataRespDTO getDictData(String dictType, String value) {
        Optional<InfraDictData> optionalDictData = infraDictDataRepository.findByDictTypeAndValue(dictType, value);
        return optionalDictData.map(DictDataConvert.INSTANCE::convert02).orElse(null);

    }

    @Override
    public DictDataRespDTO parseDictData(String dictType, String label) {
        Optional<InfraDictData> optionalDictData = infraDictDataRepository.findByDictTypeAndLabel(dictType, label);
        return optionalDictData.map(DictDataConvert.INSTANCE::convert02).orElse(null);
    }

    @Override
    public String create(DictDataCreateInput inputVO) {
        // 校验字典类型有效
        InfraDictType dictType = validateDictTypeExists(inputVO.getDictType());
        // 校验字典数据的值的唯一性
        validateDictDataValueUnique(null, inputVO.getDictType(), inputVO.getValue());

        // 插入字典类型
        InfraDictData dictData = DictDataConvert.INSTANCE.createInputConvert(inputVO);
        dictData = InfraDictDataDraft.$.produce(dictData, draft -> {
            draft.setTypeId(dictType.id());
        });
        dictData = infraDictDataRepository.insert(dictData);
        return dictData.id().toString();
    }

    @Override
    public Boolean update(DictDataUpdateInput inputVO) {
        // 校验自己存在
        validateDictDataExists(inputVO.getId());
        // 校验字典类型有效
        validateDictTypeExists(inputVO.getTypeId());
        // 校验字典数据的值的唯一性
        validateDictDataValueUnique(inputVO.getId(), inputVO.getTypeId(), inputVO.getValue());

        // 更新字典类型
        InfraDictData updateObj = DictDataConvert.INSTANCE.updateInputConvert(inputVO);
        infraDictDataRepository.update(updateObj);
        return true;
    }

    @Override
    public Boolean delete(UUID id) {
        // 校验是否存在
        validateDictDataExists(id);
        // 删除字典数据
        infraDictDataRepository.deleteById(id);
        return true;
    }

    @Override
    public List<DictDataListAllSimpleOutput> listAllSimple() {
        List<InfraDictData> list = infraDictDataRepository.findSimpleList();

        return DictDataConvert.INSTANCE.listAllSimpleListOutputConvert(list);
    }

    @Override
    public DictDataGetOutput get(UUID id) {
        Optional<InfraDictData> optionalDictData = infraDictDataRepository.findById(id);
        return optionalDictData.map(DictDataConvert.INSTANCE::getOutputConvert).orElse(null);
    }

    @Override
    public void export(HttpServletResponse response, @Valid DictDataExportInput inputVO) {
        List<InfraDictData> list = infraDictDataRepository.selectList(inputVO);
        List<DictDataExcelOutput> data = DictDataConvert.INSTANCE.listExportOutputConvert(list);
        // 输出
        try {
            ExcelUtils.write(response, "字典数据.xls", "数据列表", DictDataExcelOutput.class, data);
        }
        catch (IOException e){
            throw exception(DICT_DATA_EXPORT_EXCEPTION);
        }

    }

    @Override
    public PageResult<DictDataPageOutput> page(DictDataPageInput inputVO) {
        Page<InfraDictData> postPage = infraDictDataRepository.selectPage(inputVO);
        List<DictDataPageOutput> postList =  DictDataConvert.INSTANCE.pagePageOutputConvert(postPage);
        return new PageResult<>(postList, postPage.getTotalElements());
    }

}
