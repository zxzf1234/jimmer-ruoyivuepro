package cn.iocoder.yudao.service.convert.system.notify;

import cn.iocoder.yudao.service.vo.system.notify.message.NotifyMessageResp;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 站内信 Convert
 *
 * @author xrcoder
 */
@Mapper
public interface NotifyMessageConvert {

    NotifyMessageConvert INSTANCE = Mappers.getMapper(NotifyMessageConvert.class);

    NotifyMessageResp convert(SystemNotifyMessage bean);

    List<NotifyMessageResp> convertList(List<SystemNotifyMessage> list);

    List<NotifyMessageResp> convertPage(Page<SystemNotifyMessage> page);


}
