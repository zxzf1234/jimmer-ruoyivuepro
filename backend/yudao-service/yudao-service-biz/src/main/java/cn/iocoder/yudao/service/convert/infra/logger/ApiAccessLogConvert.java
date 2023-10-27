package cn.iocoder.yudao.service.convert.infra.logger;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.api.infra.logger.dto.ApiAccessLogCreateReqDTO;
import cn.iocoder.yudao.service.vo.infra.logger.apiaccesslog.ApiAccessLogExcelVO;
import cn.iocoder.yudao.service.vo.infra.logger.apiaccesslog.ApiAccessLogRespVO;
import cn.iocoder.yudao.service.model.infra.data.InfraApiAccessLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 访问日志 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ApiAccessLogConvert {

    ApiAccessLogConvert INSTANCE = Mappers.getMapper(ApiAccessLogConvert.class);

    ApiAccessLogRespVO convert(InfraApiAccessLog bean);

    List<ApiAccessLogRespVO> convertList(List<InfraApiAccessLog> list);

    PageResult<ApiAccessLogRespVO> convertPage(PageResult<InfraApiAccessLog> page);

    List<ApiAccessLogExcelVO> convertList02(List<InfraApiAccessLog> list);

    InfraApiAccessLog convert(ApiAccessLogCreateReqDTO bean);

}
