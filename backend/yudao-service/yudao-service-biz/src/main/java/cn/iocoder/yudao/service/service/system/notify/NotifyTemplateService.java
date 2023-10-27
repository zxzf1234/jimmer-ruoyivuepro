package cn.iocoder.yudao.service.service.system.notify;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.system.notify.template.*;
import cn.iocoder.yudao.service.vo.system.notify.template.NotifyTemplateResp;
import cn.iocoder.yudao.service.vo.system.notify.template.NotifyTemplateUpdateReq;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyTemplate;

import javax.validation.Valid;
import java.util.Map;

/**
 * 站内信模版 Service 接口
 *
 * @author xrcoder
 */
public interface NotifyTemplateService {

    /**
     * 获得站内信模板，从缓存中
     *
     * @param code 模板编码
     * @return 站内信模板
     */
    SystemNotifyTemplate getNotifyTemplateByCodeFromCache(String code);

    /**
     * 创建站内信模版
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createNotifyTemplate(@Valid NotifyTemplateCreateReq createReqVO);

    /**
     * 更新站内信模版
     *
     * @param updateReqVO 更新信息
     */
    void updateNotifyTemplate(@Valid NotifyTemplateUpdateReq updateReqVO);

    /**
     * 删除站内信模版
     *
     * @param id 编号
     */
    void deleteNotifyTemplate(Long id);

    /**
     * 获得站内信模版
     *
     * @param id 编号
     * @return 站内信模版
     */
    SystemNotifyTemplate getNotifyTemplate(Long id);

    /**
     * 获得站内信模版分页
     *
     * @param pageReqVO 分页查询
     * @return 站内信模版分页
     */
    PageResult<NotifyTemplateResp> getNotifyTemplatePage(NotifyTemplatePageReqVO pageReqVO);

    /**
     * 格式化站内信内容
     *
     * @param content 站内信模板的内容
     * @param params 站内信内容的参数
     * @return 格式化后的内容
     */
    String formatNotifyTemplateContent(String content, Map<String, Object> params);

}
