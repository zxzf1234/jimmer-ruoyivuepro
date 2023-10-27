package cn.iocoder.yudao.service.convert.infra.dict;

import cn.iocoder.yudao.service.vo.infra.permission.menu.MenuCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.permission.menu.MenuRespVO;
import cn.iocoder.yudao.service.vo.infra.permission.menu.MenuSimpleRespVO;
import cn.iocoder.yudao.service.vo.infra.permission.menu.MenuUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.data.SystemMenu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    List<MenuRespVO> convertList(List<SystemMenu> list);

    SystemMenu convert(MenuCreateReqVO bean);

    SystemMenu convert(MenuUpdateReqVO bean);

    MenuRespVO convert(SystemMenu bean);

    List<MenuSimpleRespVO> convertList02(List<SystemMenu> list);

}
