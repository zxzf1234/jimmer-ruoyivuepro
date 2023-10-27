package cn.iocoder.yudao.service.convert.infra.mail;

import cn.iocoder.yudao.service.vo.infra.mail.template.MailTemplateCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.mail.template.MailTemplateRespVO;
import cn.iocoder.yudao.service.vo.infra.mail.template.MailTemplateSimpleRespVO;
import cn.iocoder.yudao.service.vo.infra.mail.template.MailTemplateUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface MailTemplateConvert {

    MailTemplateConvert INSTANCE = Mappers.getMapper(MailTemplateConvert.class);

    SystemMailTemplate convert(MailTemplateUpdateReqVO bean);

    SystemMailTemplate convert(MailTemplateCreateReqVO bean);

    MailTemplateRespVO convert(SystemMailTemplate bean);

    List<MailTemplateRespVO> convertPage(Page<SystemMailTemplate> pageResult);

    List<MailTemplateSimpleRespVO> convertList02(List<SystemMailTemplate> list);

}
