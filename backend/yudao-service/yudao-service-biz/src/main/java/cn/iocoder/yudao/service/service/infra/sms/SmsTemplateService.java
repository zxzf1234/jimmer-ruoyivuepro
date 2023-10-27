package cn.iocoder.yudao.service.service.infra.sms;

import cn.iocoder.yudao.service.vo.infra.sms.template.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.model.infra.sms.SystemSmsTemplate;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 短信模板 Service 接口
 *
 * @author zzf
 * @date 2021/1/25 9:24
 */
public interface SmsTemplateService {

    /**
     * 格式化短信内容
     *
     * @param content 短信模板的内容
     * @param params 内容的参数
     * @return 格式化后的内容
     */
    String formatSmsTemplateContent(String content, Map<String, Object> params);

    /**
     * 获得短信模板
     *
     * @param code 模板编码
     * @return 短信模板
     */
    SystemSmsTemplate getSmsTemplateByCode(String code);

    /**
     * 创建短信模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSmsTemplate(@Valid SmsTemplateCreateReqVO createReqVO);

    /**
     * 更新短信模板
     *
     * @param updateReqVO 更新信息
     */
    void updateSmsTemplate(@Valid SmsTemplateUpdateReqVO updateReqVO);

    /**
     * 删除短信模板
     *
     * @param id 编号
     */
    void deleteSmsTemplate(Long id);

    /**
     * 获得短信模板
     *
     * @param id 编号
     * @return 短信模板
     */
    SystemSmsTemplate getSmsTemplate(Long id);

    /**
     * 获得短信模板列表
     *
     * @param ids 编号
     * @return 短信模板列表
     */
    List<SystemSmsTemplate> getSmsTemplateList(Collection<Long> ids);

    /**
     * 获得短信模板分页
     *
     * @param pageReqVO 分页查询
     * @return 短信模板分页
     */
    PageResult<SmsTemplateRespVO> getSmsTemplatePage(SmsTemplatePageReqVO pageReqVO);

    /**
     * 获得短信模板列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 短信模板分页
     */
    List<SystemSmsTemplate> getSmsTemplateList(SmsTemplateExportReqVO exportReqVO);

    /**
     * 获得指定短信渠道下的短信模板数量
     *
     * @param channelId 短信渠道编号
     * @return 数量
     */
    long countByChannelId(Long channelId);

}
