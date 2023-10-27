package cn.iocoder.yudao.service.convert.infra.dict;

import cn.iocoder.yudao.service.api.infra.errorcode.dto.ErrorCodeAutoGenerateReqDTO;
import cn.iocoder.yudao.service.api.infra.errorcode.dto.ErrorCodeRespDTO;
import cn.iocoder.yudao.service.vo.infra.errorcode.ErrorCodeCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.errorcode.ErrorCodeExcelVO;
import cn.iocoder.yudao.service.vo.infra.errorcode.ErrorCodeRespVO;
import cn.iocoder.yudao.service.vo.infra.errorcode.ErrorCodeUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.data.SystemErrorCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 错误码 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ErrorCodeConvert {

    ErrorCodeConvert INSTANCE = Mappers.getMapper(ErrorCodeConvert.class);

    SystemErrorCode convert(ErrorCodeCreateReqVO bean);

    SystemErrorCode convert(ErrorCodeUpdateReqVO bean);

    ErrorCodeRespVO convert(SystemErrorCode bean);

    List<ErrorCodeRespVO> convertList(List<SystemErrorCode> list);

    List<ErrorCodeRespVO> convertPage(Page<SystemErrorCode> page);

    List<ErrorCodeExcelVO> convertList02(List<SystemErrorCode> list);

    SystemErrorCode convert(ErrorCodeAutoGenerateReqDTO bean);

    List<ErrorCodeRespDTO> convertList03(List<SystemErrorCode> list);

}
