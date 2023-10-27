package cn.iocoder.yudao.service.service.infra.mail;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.mail.log.MailLogPageReqVO;
import cn.iocoder.yudao.service.vo.infra.mail.log.MailLogRespVO;
import cn.iocoder.yudao.service.convert.infra.mail.MailLogConvert;
import cn.iocoder.yudao.service.enums.system.mail.MailSendStatusEnum;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailAccount;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailLog;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailLogDraft;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailTemplate;
import cn.iocoder.yudao.service.repository.infra.mail.SystemMailLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.hutool.core.exceptions.ExceptionUtil.getRootCauseMessage;

/**
 * 邮件日志 Service 实现类
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
@Service
@Validated
public class MailLogServiceImpl implements MailLogService {

    @Resource
    private SystemMailLogRepository systemMailLogRepository;

    @Override
    public PageResult<MailLogRespVO> getMailLogPage(MailLogPageReqVO pageVO) {
        Page<SystemMailLog> postPage = systemMailLogRepository.selectPage(pageVO);
        List<MailLogRespVO> listPage = MailLogConvert.INSTANCE.convertPage(postPage);

        return new PageResult<>(listPage, postPage.getTotalElements());
    }

    @Override
    public SystemMailLog getMailLog(Long id) {
        return systemMailLogRepository.findById(id).get();
    }

    @Override
    public Long createMailLog(Long userId, Integer userType, String toMail,
                              SystemMailAccount account, SystemMailTemplate template,
                              String templateContent, Map<String, Object> templateParams, Boolean isSend) {
        // 插入数据库
        SystemMailLog newMailLog = SystemMailLogDraft.$.produce(SystemMailLog->{
            SystemMailLog.setSendStatus(Objects.equals(isSend, true) ? MailSendStatusEnum.INIT.getStatus()
                    : MailSendStatusEnum.IGNORE.getStatus())
                    .setUserId(userId)
                    .setUserType(userType)
                    .setToMail(toMail)
                    .setAccountId(account.id())
                    .setFromMail(account.mail())
                    .setTemplateId(template.id())
                    .setTemplateCode(template.code())
                    .setTemplateNickname(template.nickname())
                    .setTemplateTitle(template.title())
                    .setTemplateContent(templateContent)
                    .setTemplateParams(templateParams);
        });
        newMailLog = systemMailLogRepository.insert(newMailLog);
        return newMailLog.id();
    }

    @Override
    public void updateMailSendResult(Long logId, String messageId, Exception exception) {
        // 1. 成功
        if (exception == null) {
            systemMailLogRepository.update(SystemMailLogDraft.$.produce(SystemMailLog->{
                SystemMailLog
                        .setId(logId)
                        .setSendTime(LocalDateTime.now())
                        .setSendStatus(MailSendStatusEnum.SUCCESS.getStatus())
                        .setSendMessageId(messageId);
            }));
            return;
        }
        // 2. 失败
        systemMailLogRepository.update(SystemMailLogDraft.$.produce(SystemMailLog->{
            SystemMailLog
                    .setId(logId)
                    .setSendTime(LocalDateTime.now())
                    .setSendStatus(MailSendStatusEnum.SUCCESS.getStatus())
                    .setSendException(getRootCauseMessage(exception));
        }));

    }

}
