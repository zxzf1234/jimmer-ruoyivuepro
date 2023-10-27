package cn.iocoder.yudao.service.convert.infra.ip;

import cn.iocoder.yudao.framework.ip.core.Area;
import cn.iocoder.yudao.service.vo.infra.ip.AreaNodeRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AreaConvert {

    AreaConvert INSTANCE = Mappers.getMapper(AreaConvert.class);

    List<AreaNodeRespVO> convertList(List<Area> list);

}
