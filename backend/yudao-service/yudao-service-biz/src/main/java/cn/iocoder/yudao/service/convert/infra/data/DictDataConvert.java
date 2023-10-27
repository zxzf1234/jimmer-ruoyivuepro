package cn.iocoder.yudao.service.convert.infra.data;

import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataPageOutput;
import cn.iocoder.yudao.service.api.system.dict.dto.DictDataRespDTO;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataGetOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataListAllSimpleOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataUpdateInput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataCreateInput;
import cn.iocoder.yudao.service.model.infra.data.InfraDictData;
import java.util.*;

import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataExcelOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

/**
 * 字典数据 Convert
 */
@Mapper
public interface DictDataConvert {
    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);


    InfraDictData createInputConvert(DictDataCreateInput input);

    InfraDictData updateInputConvert(DictDataUpdateInput input);

    List<DictDataListAllSimpleOutput> listAllSimpleListOutputConvert(List<InfraDictData> output);

    @Mapping(source = "type.type", target = "dictType")
    DictDataListAllSimpleOutput AllSimpleListOutputConvert(InfraDictData output);

    DictDataGetOutput getOutputConvert(InfraDictData output);

    List<DictDataExcelOutput> listExportOutputConvert(List<InfraDictData> output);

    DictDataRespDTO convert02(InfraDictData bean);

    List<DictDataPageOutput> pagePageOutputConvert(Page<InfraDictData> output);

}
