package cn.iocoder.yudao.service.api.system.dict;

import cn.iocoder.yudao.service.api.system.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.service.service.infra.data.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 字典数据 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class DictDataApiImpl implements DictDataApi {

    @Resource
    private DictDataService dictDataService;

    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        dictDataService.validateDictDataList(dictType, values);
    }

    @Override
    public DictDataRespDTO getDictData(String dictType, String value) {
        return dictDataService.getDictData(dictType, value);
    }

    @Override
    public DictDataRespDTO parseDictData(String dictType, String label) {
        return dictDataService.parseDictData(dictType, label);
    }

}
