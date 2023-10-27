package cn.iocoder.yudao.service.convert.infra.logger;

import cn.iocoder.yudao.service.api.infra.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.yudao.service.vo.infra.logger.loginlog.LoginLogExcelVO;
import cn.iocoder.yudao.service.vo.infra.logger.loginlog.LoginLogRespVO;
import cn.iocoder.yudao.service.model.infra.logger.SystemLoginLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    List<LoginLogRespVO> convertPage(Page<SystemLoginLog> page);

    List<LoginLogExcelVO> convertList(List<SystemLoginLog> list);

    SystemLoginLog convert(LoginLogCreateReqDTO bean);

}
