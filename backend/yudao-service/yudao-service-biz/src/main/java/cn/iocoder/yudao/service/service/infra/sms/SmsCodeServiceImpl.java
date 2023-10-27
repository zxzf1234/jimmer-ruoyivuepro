package cn.iocoder.yudao.service.service.infra.sms;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.iocoder.yudao.service.api.system.sms.dto.code.SmsCodeSendReqDTO;
import cn.iocoder.yudao.service.api.system.sms.dto.code.SmsCodeUseReqDTO;
import cn.iocoder.yudao.service.api.system.sms.dto.code.SmsCodeValidateReqDTO;
import cn.iocoder.yudao.service.enums.system.sms.SmsSceneEnum;
import cn.iocoder.yudao.service.framework.sms.SmsCodeProperties;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsCode;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsCodeDraft;
import cn.iocoder.yudao.service.repository.infra.sms.SystemSmsCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

import static cn.hutool.core.util.RandomUtil.randomInt;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.isToday;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.*;

/**
 * 短信验证码 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SmsCodeServiceImpl implements SmsCodeService {

    @Resource
    private SmsCodeProperties smsCodeProperties;

    @Resource
    private SystemSmsCodeRepository systemSmsCodeRepository;

    @Resource
    private SmsSendService smsSendService;

    @Override
    public void sendSmsCode(SmsCodeSendReqDTO reqDTO) {
        SmsSceneEnum sceneEnum = SmsSceneEnum.getCodeByScene(reqDTO.getScene());
        Assert.notNull(sceneEnum, "验证码场景({}) 查找不到配置", reqDTO.getScene());
        // 创建验证码
        String code = createSmsCode(reqDTO.getMobile(), reqDTO.getScene(), reqDTO.getCreateIp());
        // 发送验证码
        smsSendService.sendSingleSms(reqDTO.getMobile(), null, null,
                sceneEnum.getTemplateCode(), MapUtil.of("code", code));
    }

    private String createSmsCode(String mobile, Integer scene, String ip) {
        // 校验是否可以发送验证码，不用筛选场景
        Optional<SystemSmsCode> opLastSmsCode = systemSmsCodeRepository.findByMobile(mobile);
        if (opLastSmsCode.isPresent()) {
            if (LocalDateTimeUtil.between(opLastSmsCode.get().createTime(), LocalDateTime.now()).toMillis()
                    < smsCodeProperties.getSendFrequency().toMillis()) { // 发送过于频繁
                throw exception(SMS_CODE_SEND_TOO_FAST);
            }
            if (isToday(opLastSmsCode.get().createTime()) && // 必须是今天，才能计算超过当天的上限
                    opLastSmsCode.get().todayIndex() >= smsCodeProperties.getSendMaximumQuantityPerDay()) { // 超过当天发送的上限。
                throw exception(SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY);
            }
            // TODO 芋艿：提升，每个 IP 每天可发送数量
            // TODO 芋艿：提升，每个 IP 每小时可发送数量
        }

        // 创建验证码记录
        String code = String.valueOf(randomInt(smsCodeProperties.getBeginCode(), smsCodeProperties.getEndCode() + 1));
        SystemSmsCode newSmsCode = SystemSmsCodeDraft.$.produce(SystemSmsCode->{
            SystemSmsCode.setMobile(mobile).setCode(code).setScene(scene)
                    .setTodayIndex(opLastSmsCode.isPresent() && isToday(opLastSmsCode.get().createTime()) ? opLastSmsCode.get().todayIndex() + 1 : 1)
                    .setCreateIp(ip).setUsed(false);
        });

        systemSmsCodeRepository.insert(newSmsCode);
        return code;
    }

    @Override
    public void useSmsCode(SmsCodeUseReqDTO reqDTO) {
        // 检测验证码是否有效
        SystemSmsCode lastSmsCode = validateSmsCode0(reqDTO.getMobile(), reqDTO.getCode(), reqDTO.getScene());
        // 使用验证码
        systemSmsCodeRepository.update(SystemSmsCodeDraft.$.produce(lastSmsCode, SystemSmsCode -> {
            SystemSmsCode.setUsedTime(LocalDateTime.now()).setUsedIp(reqDTO.getUsedIp());
        }));
    }

    @Override
    public void validateSmsCode(SmsCodeValidateReqDTO reqDTO) {
        validateSmsCode0(reqDTO.getMobile(), reqDTO.getCode(), reqDTO.getScene());
    }

    private SystemSmsCode validateSmsCode0(String mobile, String code, Integer scene) {
        // 校验验证码
        Optional<SystemSmsCode> opLastSmsCode = systemSmsCodeRepository.findByMobileAndCodeAndScene(mobile, code, scene);
        // 若验证码不存在，抛出异常
        if (!opLastSmsCode.isPresent()) {
            throw exception(SMS_CODE_NOT_FOUND);
        }
        // 超过时间
        if (LocalDateTimeUtil.between(opLastSmsCode.get().createTime(), LocalDateTime.now()).toMillis()
                >= smsCodeProperties.getExpireTimes().toMillis()) { // 验证码已过期
            throw exception(SMS_CODE_EXPIRED);
        }
        // 判断验证码是否已被使用
        if (Boolean.TRUE.equals(opLastSmsCode.get().used())) {
            throw exception(SMS_CODE_USED);
        }
        return opLastSmsCode.get();
    }

}
