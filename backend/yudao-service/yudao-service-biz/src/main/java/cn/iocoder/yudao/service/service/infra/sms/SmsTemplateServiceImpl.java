package cn.iocoder.yudao.service.service.infra.sms;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.sms.core.client.SmsClient;
import cn.iocoder.yudao.framework.sms.core.client.SmsClientFactory;
import cn.iocoder.yudao.framework.sms.core.client.SmsCommonResult;
import cn.iocoder.yudao.framework.sms.core.client.dto.SmsTemplateRespDTO;
import cn.iocoder.yudao.service.vo.infra.sms.template.*;
import cn.iocoder.yudao.service.convert.infra.sms.SmsTemplateConvert;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsChannel;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsTemplate;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsTemplateDraft;
import cn.iocoder.yudao.service.mq.producer.sms.SmsProducer;
import cn.iocoder.yudao.service.repository.infra.sms.SystemSmsTemplateRepository;
import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.*;

/**
 * 短信模板 Service 实现类
 *
 * @author zzf
 * @since 2021/1/25 9:25
 */
@Service
@Slf4j
public class SmsTemplateServiceImpl implements SmsTemplateService {

    /**
     * 正则表达式，匹配 {} 中的变量
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");

    @Resource
    private SystemSmsTemplateRepository systemSmsTemplateRepository;

    @Resource
    private SmsChannelService smsChannelService;

    @Resource
    private SmsClientFactory smsClientFactory;

    @Override
    public String formatSmsTemplateContent(String content, Map<String, Object> params) {
        return StrUtil.format(content, params);
    }

    @Override
    public SystemSmsTemplate getSmsTemplateByCode(String code) {
        return systemSmsTemplateRepository.findByName(code).get();
    }

    @VisibleForTesting
    public List<String> parseTemplateContentParams(String content) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, content);
    }

    @Override
    public Long createSmsTemplate(SmsTemplateCreateReqVO createReqVO) {
        // 校验短信渠道
        SystemSmsChannel channelDO = validateSmsChannel(createReqVO.getChannelId());
        // 校验短信编码是否重复
        validateSmsTemplateCodeDuplicate(null, createReqVO.getCode());
        // 校验短信模板
        validateApiTemplate(createReqVO.getChannelId(), createReqVO.getApiTemplateId());

        // 插入
        SystemSmsTemplate template = SmsTemplateConvert.INSTANCE.convert(createReqVO);
        SystemSmsTemplate finalTemplate = template;
        template = SystemSmsTemplateDraft.$.produce(template, SystemSmsTemplate->{
            SystemSmsTemplate.setParams(parseTemplateContentParams(finalTemplate.content()))
                    .setChannelCode(channelDO.code());
        });
        template = systemSmsTemplateRepository.insert(template);
        // 返回
        return template.id();
    }

    @Override
    public void updateSmsTemplate(SmsTemplateUpdateReqVO updateReqVO) {
        // 校验存在
        validateSmsTemplateExists(updateReqVO.getId());
        // 校验短信渠道
        SystemSmsChannel channelDO = validateSmsChannel(updateReqVO.getChannelId());
        // 校验短信编码是否重复
        validateSmsTemplateCodeDuplicate(updateReqVO.getId(), updateReqVO.getCode());
        // 校验短信模板
        validateApiTemplate(updateReqVO.getChannelId(), updateReqVO.getApiTemplateId());

        // 更新
        SystemSmsTemplate updateObj = SmsTemplateConvert.INSTANCE.convert(updateReqVO);
        SystemSmsTemplate finalUpdateObj = updateObj;
        updateObj = SystemSmsTemplateDraft.$.produce(updateObj, SystemSmsTemplate->{
            SystemSmsTemplate.setParams(parseTemplateContentParams(finalUpdateObj.content()))
                    .setChannelCode(channelDO.code());
        });
        systemSmsTemplateRepository.update(updateObj);
    }

    @Override
    public void deleteSmsTemplate(Long id) {
        // 校验存在
        validateSmsTemplateExists(id);
        // 更新
        systemSmsTemplateRepository.deleteById(id);
    }

    private void validateSmsTemplateExists(Long id) {
        if (!systemSmsTemplateRepository.findById(id).isPresent() ) {
            throw exception(SMS_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public SystemSmsTemplate getSmsTemplate(Long id) {
        return systemSmsTemplateRepository.findById(id).get();
    }

    @Override
    public List<SystemSmsTemplate> getSmsTemplateList(Collection<Long> ids) {
        return systemSmsTemplateRepository.findByIds(ids);
    }

    @Override
    public PageResult<SmsTemplateRespVO> getSmsTemplatePage(SmsTemplatePageReqVO pageReqVO) {
        Page<SystemSmsTemplate> postPage = systemSmsTemplateRepository.selectPage(pageReqVO);
        List<SmsTemplateRespVO> postList = SmsTemplateConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(postList, postPage.getTotalElements());
    }

    @Override
    public List<SystemSmsTemplate> getSmsTemplateList(SmsTemplateExportReqVO exportReqVO) {
        return systemSmsTemplateRepository.selectList(exportReqVO);
    }

    @Override
    public long countByChannelId(Long channelId) {
        return systemSmsTemplateRepository.countByChannelId(channelId);
    }

    @VisibleForTesting
    public SystemSmsChannel validateSmsChannel(Long channelId) {
        SystemSmsChannel channelDO = smsChannelService.getSmsChannel(channelId);
        if (channelDO == null) {
            throw exception(SMS_CHANNEL_NOT_EXISTS);
        }
        if (!Objects.equals(channelDO.status(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(SMS_CHANNEL_DISABLE);
        }
        return channelDO;
    }

    @VisibleForTesting
    public void validateSmsTemplateCodeDuplicate(Long id, String code) {
        Optional<SystemSmsTemplate> opTemplate = systemSmsTemplateRepository.findByCode(code);
        if (!opTemplate.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(SMS_TEMPLATE_CODE_DUPLICATE, code);
        }
        if (opTemplate.get().id() != id) {
            throw exception(SMS_TEMPLATE_CODE_DUPLICATE, code);
        }
    }

    /**
     * 校验 API 短信平台的模板是否有效
     *
     * @param channelId 渠道编号
     * @param apiTemplateId API 模板编号
     */
    @VisibleForTesting
    public void validateApiTemplate(Long channelId, String apiTemplateId) {
        // 获得短信模板
        SmsClient smsClient = smsClientFactory.getSmsClient(channelId);
        Assert.notNull(smsClient, String.format("短信客户端(%d) 不存在", channelId));
        SmsCommonResult<SmsTemplateRespDTO> templateResult = smsClient.getSmsTemplate(apiTemplateId);
        // 校验短信模板是否正确
        templateResult.checkError();
    }

}
