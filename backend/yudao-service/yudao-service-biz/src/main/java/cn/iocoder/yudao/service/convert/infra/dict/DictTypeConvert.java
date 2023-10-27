package cn.iocoder.yudao.service.convert.infra.dict;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface DictTypeConvert {

    DictTypeConvert INSTANCE = Mappers.getMapper(DictTypeConvert.class);


}
