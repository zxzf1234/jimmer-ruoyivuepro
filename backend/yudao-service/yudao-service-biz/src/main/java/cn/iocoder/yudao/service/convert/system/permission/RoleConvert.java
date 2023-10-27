package cn.iocoder.yudao.service.convert.system.permission;

import cn.iocoder.yudao.service.vo.infra.permission.role.*;
import cn.iocoder.yudao.service.model.system.permission.SystemRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    SystemRole convert(RoleUpdateReqVO bean);

    RoleRespVO convert(SystemRole bean);

    SystemRole convert(RoleCreateReqVO bean);

    List<RoleSimpleRespVO> convertList02(List<SystemRole> list);

    List<RoleExcelVO> convertList03(List<SystemRole> list);

}
