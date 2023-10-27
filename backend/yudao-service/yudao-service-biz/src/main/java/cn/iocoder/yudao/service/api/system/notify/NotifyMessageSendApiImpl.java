package cn.iocoder.yudao.service.api.system.notify;

import cn.iocoder.yudao.service.api.system.notify.dto.NotifySendSingleToUserReqDTO;
import cn.iocoder.yudao.service.service.system.notify.NotifySendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 站内信发送 API 实现类
 *
 * @author xrcoder
 */
@Service
public class NotifyMessageSendApiImpl implements NotifyMessageSendApi {

    @Resource
    private NotifySendService notifySendService;

    @Override
    public Long sendSingleMessageToAdmin(NotifySendSingleToUserReqDTO reqDTO) {
        return notifySendService.sendSingleNotifyToAdmin(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

    @Override
    public Long sendSingleMessageToMember(NotifySendSingleToUserReqDTO reqDTO) {
        return notifySendService.sendSingleNotifyToMember(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

}
