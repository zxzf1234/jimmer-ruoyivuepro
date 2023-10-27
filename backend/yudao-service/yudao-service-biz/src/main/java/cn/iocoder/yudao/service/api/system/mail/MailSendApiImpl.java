package cn.iocoder.yudao.service.api.system.mail;

import cn.iocoder.yudao.service.api.system.mail.dto.MailSendSingleToUserReqDTO;
import cn.iocoder.yudao.service.service.infra.mail.MailSendService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 邮件发送 API 实现类
 *
 * @author wangjingyi
 */
@Service
@Validated
public class MailSendApiImpl implements MailSendApi {

    @Resource
    private MailSendService mailSendService;

    @Override
    public Long sendSingleMailToAdmin(MailSendSingleToUserReqDTO reqDTO) {
        return mailSendService.sendSingleMailToAdmin(reqDTO.getMail(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

    @Override
    public Long sendSingleMailToMember(MailSendSingleToUserReqDTO reqDTO) {
        return mailSendService.sendSingleMailToMember(reqDTO.getMail(), reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

}
