package cn.iocoder.yudao.service.convert.infra.sms;

import cn.iocoder.yudao.service.vo.infra.sms.log.SmsLogExcelVO;
import cn.iocoder.yudao.service.vo.infra.sms.log.SmsLogRespVO;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 短信日志 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface SmsLogConvert {

    SmsLogConvert INSTANCE = Mappers.getMapper(SmsLogConvert.class);

    SmsLogRespVO convert(SystemSmsLog bean);

    List<SmsLogRespVO> convertList(List<SystemSmsLog> list);

    List<SmsLogRespVO> convertPage(Page<SystemSmsLog> page);

    List<SmsLogExcelVO> convertList02(List<SystemSmsLog> list);

}
