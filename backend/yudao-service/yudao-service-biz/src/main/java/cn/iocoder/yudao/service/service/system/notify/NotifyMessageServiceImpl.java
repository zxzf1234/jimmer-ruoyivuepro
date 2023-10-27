package cn.iocoder.yudao.service.service.system.notify;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.system.notify.message.NotifyMessageMyPageReqVO;
import cn.iocoder.yudao.service.vo.system.notify.message.NotifyMessagePageReqVO;
import cn.iocoder.yudao.service.vo.system.notify.message.NotifyMessageResp;
import cn.iocoder.yudao.service.convert.system.notify.NotifyMessageConvert;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyMessage;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyMessageDraft;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyTemplate;
import cn.iocoder.yudao.service.repository.system.notify.SystemNotifyMessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 站内信 Service 实现类
 *
 * @author xrcoder
 */
@Service
@Validated
public class NotifyMessageServiceImpl implements NotifyMessageService {

    @Resource
    private SystemNotifyMessageRepository systemNotifyMessageRepository;

    @Override
    public Long createNotifyMessage(Long userId, Integer userType,
                                    SystemNotifyTemplate template, String templateContent, Map<String, Object> templateParams) {
        SystemNotifyMessage message = SystemNotifyMessageDraft.$.produce(SystemNotifyMessage->{
            SystemNotifyMessage
                    .setUserId(userId)
                    .setUserType(userType)
                    .setTemplateId(template.id())
                    .setTemplateCode(template.code())
                    .setTemplateType(template.type())
                    .setTemplateNickname(template.nickname())
                    .setTemplateContent(templateContent)
                    .setTemplateParams(templateParams).setReadStatus(false);
        });
        message = systemNotifyMessageRepository.insert(message);
        return message.id();
    }

    @Override
    public PageResult<NotifyMessageResp> getNotifyMessagePage(NotifyMessagePageReqVO pageReqVO) {
        Page<SystemNotifyMessage> postPage = systemNotifyMessageRepository.selectPage(pageReqVO);
        List<NotifyMessageResp> listPage = NotifyMessageConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(listPage, postPage.getTotalElements());
    }

    @Override
    public PageResult<NotifyMessageResp> getMyMyNotifyMessagePage(NotifyMessageMyPageReqVO pageReqVO, Long userId, Integer userType) {
        Page<SystemNotifyMessage> postPage = systemNotifyMessageRepository.selectPage(pageReqVO, userId, userType);
        List<NotifyMessageResp> listPage = NotifyMessageConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(listPage, postPage.getTotalElements());
    }

    @Override
    public SystemNotifyMessage getNotifyMessage(Long id) {
        return systemNotifyMessageRepository.findById(id).get();
    }

    @Override
    public List<SystemNotifyMessage> getUnreadNotifyMessageList(Long userId, Integer userType, Integer size) {
        return systemNotifyMessageRepository.getUnreadNotifyMessageList(userId, userType, size);
    }

    @Override
    public long getUnreadNotifyMessageCount(Long userId, Integer userType) {
        return systemNotifyMessageRepository.countByUserIdAndUserType(userId, userType);
    }

    @Override
    public int updateNotifyMessageRead(Collection<Long> ids, Long userId, Integer userType) {
        return systemNotifyMessageRepository.updateNotifyMessageRead(ids, userId, userType);
    }

    @Override
    public int updateAllNotifyMessageRead(Long userId, Integer userType) {
        return systemNotifyMessageRepository.updateAllNotifyMessageRead(userId,userType);
//                updateListRead(userId, userType);
    }

}
