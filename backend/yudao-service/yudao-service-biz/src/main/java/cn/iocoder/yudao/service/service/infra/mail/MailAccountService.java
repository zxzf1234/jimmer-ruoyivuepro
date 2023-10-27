package cn.iocoder.yudao.service.service.infra.mail;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.mail.account.MailAccountBaseVO;
import cn.iocoder.yudao.service.vo.infra.mail.account.MailAccountCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.mail.account.MailAccountPageReqVO;
import cn.iocoder.yudao.service.vo.infra.mail.account.MailAccountUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailAccount;

import javax.validation.Valid;
import java.util.List;

/**
 * 邮箱账号 Service 接口
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
public interface MailAccountService {

    /**
     * 创建邮箱账号
     *
     * @param createReqVO 邮箱账号信息
     * @return 编号
     */
    Long createMailAccount(@Valid MailAccountCreateReqVO createReqVO);

    /**
     * 修改邮箱账号
     *
     * @param updateReqVO 邮箱账号信息
     */
    void updateMailAccount(@Valid MailAccountUpdateReqVO updateReqVO);

    /**
     * 删除邮箱账号
     *
     * @param id 编号
     */
    void deleteMailAccount(Long id);

    /**
     * 获取邮箱账号信息
     *
     * @param id 编号
     * @return 邮箱账号信息
     */
    SystemMailAccount getMailAccount(Long id);

    /**
     * 获取邮箱账号分页信息
     *
     * @param pageReqVO 邮箱账号分页参数
     * @return 邮箱账号分页信息
     */
    PageResult<MailAccountBaseVO> getMailAccountPage(MailAccountPageReqVO pageReqVO);

    /**
     * 获取邮箱数组信息
     *
     * @return 邮箱账号信息数组
     */
    List<SystemMailAccount> getMailAccountList();

}
