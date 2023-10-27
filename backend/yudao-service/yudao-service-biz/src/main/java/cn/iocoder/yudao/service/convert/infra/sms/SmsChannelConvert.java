package cn.iocoder.yudao.service.convert.infra.sms;

import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelRespVO;
import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelSimpleRespVO;
import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelUpdateReqVO;
import cn.iocoder.yudao.framework.sms.core.property.SmsChannelProperties;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsChannel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 短信渠道 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface SmsChannelConvert {

    SmsChannelConvert INSTANCE = Mappers.getMapper(SmsChannelConvert.class);

    SystemSmsChannel convert(SmsChannelCreateReqVO bean);

    SystemSmsChannel convert(SmsChannelUpdateReqVO bean);

    SmsChannelRespVO convert(SystemSmsChannel bean);

    List<SmsChannelRespVO> convertList(List<SystemSmsChannel> list);

    List<SmsChannelRespVO> convertPage(Page<SystemSmsChannel> page);

    List<SmsChannelProperties> convertList02(List<SystemSmsChannel> list);

    List<SmsChannelSimpleRespVO> convertList03(List<SystemSmsChannel> list);

}
