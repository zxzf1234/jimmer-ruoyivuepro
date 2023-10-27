package cn.iocoder.yudao.service.convert.infra.mail;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.iocoder.yudao.service.vo.infra.mail.account.*;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailAccount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface MailAccountConvert {

    MailAccountConvert INSTANCE = Mappers.getMapper(MailAccountConvert.class);

    SystemMailAccount convert(MailAccountCreateReqVO bean);

    SystemMailAccount convert(MailAccountUpdateReqVO bean);

    MailAccountRespVO convert(SystemMailAccount bean);

    List<MailAccountBaseVO> convertPage(Page<SystemMailAccount> pageResult);

    List<MailAccountSimpleRespVO> convertList02(List<SystemMailAccount> list);

    default MailAccount convert(SystemMailAccount account, String nickname) {
        String from = StrUtil.isNotEmpty(nickname) ? nickname + " <" + account.mail() + ">" : account.mail();
        return new MailAccount().setFrom(from).setAuth(true)
                .setUser(account.username()).setPass(account.password())
                .setHost(account.host()).setPort(account.port()).setSslEnable(account.sslEnable());
    }

}
