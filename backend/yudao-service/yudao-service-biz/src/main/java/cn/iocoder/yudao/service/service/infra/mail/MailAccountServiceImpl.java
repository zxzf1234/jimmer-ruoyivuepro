package cn.iocoder.yudao.service.service.infra.mail;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.mail.account.MailAccountBaseVO;
import cn.iocoder.yudao.service.vo.infra.mail.account.MailAccountCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.mail.account.MailAccountPageReqVO;
import cn.iocoder.yudao.service.vo.infra.mail.account.MailAccountUpdateReqVO;
import cn.iocoder.yudao.service.convert.infra.mail.MailAccountConvert;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailAccount;
import cn.iocoder.yudao.service.repository.infra.mail.SystemMailAccountRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.Page;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.*;

/**
 * 邮箱账号 Service 实现类
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
@Service
@Validated
@Slf4j
public class MailAccountServiceImpl implements MailAccountService {

    @Resource
    private SystemMailAccountRepository systemMailAccountRepository;

    @Resource
    private MailTemplateService mailTemplateService;


    @Override
    public SystemMailAccount getMailAccount(Long id) {
        return systemMailAccountRepository.findById(id).get();
    }

    @Override
    public Long createMailAccount(MailAccountCreateReqVO createReqVO) {
        // 插入
        SystemMailAccount account = MailAccountConvert.INSTANCE.convert(createReqVO);
        account = systemMailAccountRepository.insert(account);

        return account.id();
    }

    @Override
    public void updateMailAccount(MailAccountUpdateReqVO updateReqVO) {
        // 校验是否存在
        validateMailAccountExists(updateReqVO.getId());

        // 更新
        SystemMailAccount updateObj = MailAccountConvert.INSTANCE.convert(updateReqVO);
        systemMailAccountRepository.update(updateObj);
    }

    @Override
    public void deleteMailAccount(Long id) {
        // 校验是否存在账号
        validateMailAccountExists(id);
        // 校验是否存在关联模版
        if (mailTemplateService.countByAccountId(id) > 0) {
            throw exception(MAIL_ACCOUNT_RELATE_TEMPLATE_EXISTS);
        }

        // 删除
        systemMailAccountRepository.deleteById(id);
    }

    private void validateMailAccountExists(Long id) {
        if (!systemMailAccountRepository.findById(id).isPresent()) {
            throw exception(MAIL_ACCOUNT_NOT_EXISTS);
        }
    }

    @Override
    public PageResult<MailAccountBaseVO> getMailAccountPage(MailAccountPageReqVO pageReqVO) {
        Page<SystemMailAccount> postPage = systemMailAccountRepository.selectPage(pageReqVO);
        List<MailAccountBaseVO> postList = MailAccountConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(postList, postPage.getTotalElements());
    }

    @Override
    public List<SystemMailAccount> getMailAccountList() {
        return systemMailAccountRepository.findAll();
    }

}
