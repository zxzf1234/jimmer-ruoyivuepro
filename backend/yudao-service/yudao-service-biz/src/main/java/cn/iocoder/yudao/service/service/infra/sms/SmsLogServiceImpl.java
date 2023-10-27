package cn.iocoder.yudao.service.service.infra.sms;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.service.vo.infra.sms.log.SmsLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.sms.log.SmsLogPageReqVO;
import cn.iocoder.yudao.service.vo.infra.sms.log.SmsLogRespVO;
import cn.iocoder.yudao.service.convert.infra.sms.SmsLogConvert;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.enums.system.sms.SmsReceiveStatusEnum;
import cn.iocoder.yudao.service.enums.system.sms.SmsSendStatusEnum;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsLog;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsLogDraft;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsTemplate;
import cn.iocoder.yudao.service.repository.infra.sms.SystemSmsLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 短信日志 Service 实现类
 *
 * @author zzf
 */
@Slf4j
@Service
public class SmsLogServiceImpl implements SmsLogService {

    @Resource
    private SystemSmsLogRepository systemSmsLogRepository;

    @Override
    public Long createSmsLog(String mobile, Long userId, Integer userType, Boolean isSend,
                             SystemSmsTemplate template, String templateContent, Map<String, Object> templateParams) {
        SystemSmsLog systemSmsLog = SystemSmsLogDraft.$.produce(SystemSmsLog->{
            SystemSmsLog
                    .setSendStatus(Objects.equals(isSend, true) ? SmsSendStatusEnum.INIT.getStatus() : SmsSendStatusEnum.IGNORE.getStatus())
                    .setMobile(mobile).setUserId(userId).setUserType(userType)
                    .setTemplateId(template.id()).setTemplateCode(template.code()).setTemplateType(template.type())
                    .setTemplateContent(templateContent).setTemplateParams(templateParams)
                    .setApiTemplateId(template.apiTemplateId())
                    .setChannelId(template.channelId()).setChannelCode(template.channelCode())
                    .setReceiveStatus(SmsReceiveStatusEnum.INIT.getStatus());
        });

        // 插入数据库
        systemSmsLog = systemSmsLogRepository.insert(systemSmsLog);
        return systemSmsLog.id();
    }

    @Override
    public void updateSmsSendResult(Long id, Integer sendCode, String sendMsg,
                                    String apiSendCode, String apiSendMsg,
                                    String apiRequestId, String apiSerialNo) {
        SmsSendStatusEnum sendStatus = CommonResult.isSuccess(sendCode) ?
                SmsSendStatusEnum.SUCCESS : SmsSendStatusEnum.FAILURE;
        systemSmsLogRepository.update(SystemSmsLogDraft.$.produce(SystemSmsLog->{
            SystemSmsLog.setId(id).setSendStatus(sendStatus.getStatus())
                    .setSendTime(LocalDateTime.now()).setSendCode(sendCode).setSendMsg(sendMsg)
                    .setApiSendCode(apiSendCode).setApiSendMsg(apiSendMsg)
                    .setApiRequestId(apiRequestId).setApiSerialNo(apiSerialNo);
        }));
    }

    @Override
    public void updateSmsReceiveResult(Long id, Boolean success, LocalDateTime receiveTime,
                                       String apiReceiveCode, String apiReceiveMsg) {
        SmsReceiveStatusEnum receiveStatus = Objects.equals(success, true) ?
                SmsReceiveStatusEnum.SUCCESS : SmsReceiveStatusEnum.FAILURE;
        systemSmsLogRepository.update(SystemSmsLogDraft.$.produce(SystemSmsLog->{
            SystemSmsLog.setId(id).setReceiveStatus(receiveStatus.getStatus())
                    .setReceiveTime(receiveTime).setApiReceiveCode(apiReceiveCode).setApiReceiveMsg(apiReceiveMsg);
        }));
    }

    @Override
    public PageResult<SmsLogRespVO> getSmsLogPage(SmsLogPageReqVO pageReqVO) {
        Page<SystemSmsLog> postPage = systemSmsLogRepository.selectPage(pageReqVO);
        List<SmsLogRespVO> postList = SmsLogConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(postList, postPage.getTotalElements());
    }

    @Override
    public List<SystemSmsLog> getSmsLogList(SmsLogExportReqVO exportReqVO) {
        return systemSmsLogRepository.selectList(exportReqVO);
    }

}
