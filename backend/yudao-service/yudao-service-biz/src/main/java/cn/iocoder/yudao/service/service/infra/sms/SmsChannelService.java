package cn.iocoder.yudao.service.service.infra.sms;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelPageReqVO;
import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelRespVO;
import cn.iocoder.yudao.service.vo.infra.sms.channel.SmsChannelUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsChannel;

import javax.validation.Valid;
import java.util.List;

/**
 * 短信渠道 Service 接口
 *
 * @author zzf
 * @date 2021/1/25 9:24
 */
public interface SmsChannelService {

    /**
     * 初始化短信客户端
     */
    void initLocalCache();

    /**
     * 创建短信渠道
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSmsChannel(@Valid SmsChannelCreateReqVO createReqVO);

    /**
     * 更新短信渠道
     *
     * @param updateReqVO 更新信息
     */
    void updateSmsChannel(@Valid SmsChannelUpdateReqVO updateReqVO);

    /**
     * 删除短信渠道
     *
     * @param id 编号
     */
    void deleteSmsChannel(Long id);

    /**
     * 获得短信渠道
     *
     * @param id 编号
     * @return 短信渠道
     */
    SystemSmsChannel getSmsChannel(Long id);

    /**
     * 获得所有短信渠道列表
     *
     * @return 短信渠道列表
     */
    List<SystemSmsChannel> getSmsChannelList();

    /**
     * 获得短信渠道分页
     *
     * @param pageReqVO 分页查询
     * @return 短信渠道分页
     */
    PageResult<SmsChannelRespVO> getSmsChannelPage(SmsChannelPageReqVO pageReqVO);

}
