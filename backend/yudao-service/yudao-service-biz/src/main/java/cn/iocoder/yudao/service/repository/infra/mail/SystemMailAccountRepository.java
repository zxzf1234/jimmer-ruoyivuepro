package cn.iocoder.yudao.service.repository.infra.mail;

import cn.iocoder.yudao.service.vo.infra.mail.account.MailAccountPageReqVO;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailAccount;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailAccountTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

public interface SystemMailAccountRepository extends JRepository<SystemMailAccount, Long> {
    SystemMailAccountTable systemMailAccountTable = SystemMailAccountTable.$;

    default Page<SystemMailAccount> selectPage(MailAccountPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemMailAccountTable)
                        .whereIf(StringUtils.hasText(reqVO.getMail()),systemMailAccountTable.mail().eq(reqVO.getMail()))
                        .whereIf(StringUtils.hasText(reqVO.getUsername()),systemMailAccountTable.username().eq(reqVO.getUsername()))
                        .select(systemMailAccountTable)
        );
    }

}
