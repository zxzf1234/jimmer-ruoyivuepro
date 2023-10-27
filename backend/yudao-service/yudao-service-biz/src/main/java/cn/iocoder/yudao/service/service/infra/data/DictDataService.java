package cn.iocoder.yudao.service.service.infra.data;

import cn.iocoder.yudao.service.api.system.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.service.model.infra.data.InfraDictData;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataCreateInput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataUpdateInput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataGetOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataPageOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataPageInput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * 字典数据 Service 接口
 *
 * @author ruoyi
 */
public interface DictDataService {

    /**
     * 校验字典数据们是否有效。如下情况，视为无效：
     * 1. 字典数据不存在
     * 2. 字典数据被禁用
     *
     * @param dictType 字典类型
     * @param values 字典数据值的数组
     */
    void validateDictDataList(String dictType, Collection<String> values);

    /**
     * 获得指定的字典数据
     *
     * @param dictType 字典类型
     * @param value 字典数据值
     * @return 字典数据
     */
    DictDataRespDTO getDictData(String dictType, String value);

    /**
     * 解析获得指定的字典数据，从缓存中
     *
     * @param dictType 字典类型
     * @param label 字典数据标签
     * @return 字典数据
     */
    DictDataRespDTO parseDictData(String dictType, String label);

    String create(DictDataCreateInput inputVO);

    Boolean update(DictDataUpdateInput inputVO);

    Boolean delete(UUID id);

    List<DictDataListAllSimpleOutput> listAllSimple();

    DictDataGetOutput get(UUID id);

    void export(HttpServletResponse response, @Valid DictDataExportInput inputVO);

    PageResult<DictDataPageOutput> page(DictDataPageInput inputVO);


}
