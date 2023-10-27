package cn.iocoder.yudao.service.convert.system.notice;

import cn.iocoder.yudao.service.vo.system.notify.notice.NoticeCreateReq;
import cn.iocoder.yudao.service.vo.system.notify.notice.NoticeResp;
import cn.iocoder.yudao.service.vo.system.notify.notice.NoticeUpdateReq;
import cn.iocoder.yudao.service.model.system.notify.SystemNotice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);

    List<NoticeResp> NoticeRespVO(Page<SystemNotice> page);

    NoticeResp convert(SystemNotice bean);

    SystemNotice convert(NoticeUpdateReq bean);

    SystemNotice convert(NoticeCreateReq bean);

}
