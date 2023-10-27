package cn.iocoder.yudao.service.convert.infra.mail;

import cn.iocoder.yudao.service.vo.infra.mail.log.MailLogRespVO;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface MailLogConvert {

    MailLogConvert INSTANCE = Mappers.getMapper(MailLogConvert.class);

    List<MailLogRespVO> convertPage(Page<SystemMailLog> pageResult);

    MailLogRespVO convert(SystemMailLog bean);

}
