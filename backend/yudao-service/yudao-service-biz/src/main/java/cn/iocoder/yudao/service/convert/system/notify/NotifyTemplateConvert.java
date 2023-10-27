package cn.iocoder.yudao.service.convert.system.notify;

import java.util.*;

import cn.iocoder.yudao.service.vo.system.notify.template.NotifyTemplateCreateReq;
import cn.iocoder.yudao.service.vo.system.notify.template.NotifyTemplateResp;
import cn.iocoder.yudao.service.vo.system.notify.template.NotifyTemplateUpdateReq;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

/**
 * 站内信模版 Convert
 *
 * @author xrcoder
 */
@Mapper
public interface NotifyTemplateConvert {

    NotifyTemplateConvert INSTANCE = Mappers.getMapper(NotifyTemplateConvert.class);

    SystemNotifyTemplate convert(NotifyTemplateCreateReq bean);

    SystemNotifyTemplate convert(NotifyTemplateUpdateReq bean);

    NotifyTemplateResp convert(SystemNotifyTemplate bean);

    List<NotifyTemplateResp> convertList(List<SystemNotifyTemplate> list);

    List<NotifyTemplateResp> convertPage(Page<SystemNotifyTemplate> page);

}
