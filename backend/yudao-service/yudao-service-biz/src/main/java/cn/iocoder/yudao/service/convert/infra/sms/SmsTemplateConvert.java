package cn.iocoder.yudao.service.convert.infra.sms;

import cn.iocoder.yudao.service.vo.infra.sms.template.SmsTemplateCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.sms.template.SmsTemplateExcelVO;
import cn.iocoder.yudao.service.vo.infra.sms.template.SmsTemplateRespVO;
import cn.iocoder.yudao.service.vo.infra.sms.template.SmsTemplateUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface SmsTemplateConvert {

    SmsTemplateConvert INSTANCE = Mappers.getMapper(SmsTemplateConvert.class);

    SystemSmsTemplate convert(SmsTemplateCreateReqVO bean);

    SystemSmsTemplate convert(SmsTemplateUpdateReqVO bean);

    SmsTemplateRespVO convert(SystemSmsTemplate bean);

    List<SmsTemplateRespVO> convertList(List<SystemSmsTemplate> list);

    List<SmsTemplateRespVO> convertPage(Page<SystemSmsTemplate> page);

    List<SmsTemplateExcelVO> convertList02(List<SystemSmsTemplate> list);

}
