package cn.iocoder.yudao.service.service.infra.sms;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.sms.core.client.SmsClientFactory;
import cn.iocoder.yudao.framework.sms.core.property.SmsChannelProperties;
import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelPageReqVO;
import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelRespVO;
import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelUpdateReqVO;
import cn.iocoder.yudao.service.convert.infra.sms.SmsChannelConvert;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsChannel;
import cn.iocoder.yudao.service.mq.producer.sms.SmsProducer;
import cn.iocoder.yudao.service.repository.infra.sms.SystemSmsChannelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.SMS_CHANNEL_HAS_CHILDREN;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.SMS_CHANNEL_NOT_EXISTS;

/**
 * 短信渠道 Service 实现类
 *
 * @author zzf
 */
@Service
@Slf4j
public class SmsChannelServiceImpl implements SmsChannelService {

    @Resource
    private SmsClientFactory smsClientFactory;

    @Resource
    private SystemSmsChannelRepository systemSmsChannelRepository;

    @Resource
    private SmsTemplateService smsTemplateService;

    @Resource
    private SmsProducer smsProducer;

    @Override
    @PostConstruct
    public void initLocalCache() {
        // 第一步：查询数据
        List<SystemSmsChannel> channels = systemSmsChannelRepository.findAll();
        log.info("[initLocalCache][缓存短信渠道，数量为:{}]", channels.size());

        // 第二步：构建缓存：创建或更新短信 Client
        List<SmsChannelProperties> propertiesList = SmsChannelConvert.INSTANCE.convertList02(channels);
        propertiesList.forEach(properties -> smsClientFactory.createOrUpdateSmsClient(properties));
    }

    @Override
    public Long createSmsChannel(SmsChannelCreateReqVO createReqVO) {
        // 插入
        SystemSmsChannel smsChannel = SmsChannelConvert.INSTANCE.convert(createReqVO);
        smsChannel = systemSmsChannelRepository.insert(smsChannel);
        // 发送刷新消息
        smsProducer.sendSmsChannelRefreshMessage();
        // 返回
        return smsChannel.id();
    }

    @Override
    public void updateSmsChannel(SmsChannelUpdateReqVO updateReqVO) {
        // 校验存在
        validateSmsChannelExists(updateReqVO.getId());
        // 更新
        SystemSmsChannel updateObj = SmsChannelConvert.INSTANCE.convert(updateReqVO);
        systemSmsChannelRepository.update(updateObj);
        // 发送刷新消息
        smsProducer.sendSmsChannelRefreshMessage();
    }

    @Override
    public void deleteSmsChannel(Long id) {
        // 校验存在
        validateSmsChannelExists(id);
        // 校验是否有在使用该账号的模版
        if (smsTemplateService.countByChannelId(id) > 0) {
            throw exception(SMS_CHANNEL_HAS_CHILDREN);
        }
        // 删除
        systemSmsChannelRepository.deleteById(id);
        // 发送刷新消息
        smsProducer.sendSmsChannelRefreshMessage();
    }

    private void validateSmsChannelExists(Long id) {
        if (!systemSmsChannelRepository.findById(id).isPresent()) {
            throw exception(SMS_CHANNEL_NOT_EXISTS);
        }
    }

    @Override
    public SystemSmsChannel getSmsChannel(Long id) {
        return systemSmsChannelRepository.findById(id).get();
    }

    @Override
    public List<SystemSmsChannel> getSmsChannelList() {
        return systemSmsChannelRepository.findAll();
    }

    @Override
    public PageResult<SmsChannelRespVO> getSmsChannelPage(SmsChannelPageReqVO pageReqVO) {
        Page<SystemSmsChannel> postPage = systemSmsChannelRepository.selectPage(pageReqVO);
        List<SmsChannelRespVO> postList = SmsChannelConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(postList, postPage.getTotalElements());
    }

}
