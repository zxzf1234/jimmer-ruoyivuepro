package cn.iocoder.yudao.service.service.system.notify;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.service.vo.system.notify.template.*;
import cn.iocoder.yudao.service.vo.system.notify.template.NotifyTemplateCreateReq;
import cn.iocoder.yudao.service.vo.system.notify.template.NotifyTemplateResp;
import cn.iocoder.yudao.service.convert.system.notify.NotifyTemplateConvert;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyTemplate;
import cn.iocoder.yudao.service.model.system.notify.SystemNotifyTemplateDraft;
import cn.iocoder.yudao.service.repository.system.notify.SystemNotifyTemplateRepository;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.*;

/**
 * 站内信模版 Service 实现类
 *
 * @author xrcoder
 */
@Service
@Validated
@Slf4j
public class NotifyTemplateServiceImpl implements NotifyTemplateService {

    /**
     * 正则表达式，匹配 {} 中的变量
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");

    @Resource
    private SystemNotifyTemplateRepository systemNotifyTemplateRepository;

    @Override
    public SystemNotifyTemplate getNotifyTemplateByCodeFromCache(String code) {
        return systemNotifyTemplateRepository.findByCode(code).get();
    }

    @Override
    public Long createNotifyTemplate(NotifyTemplateCreateReq createReqVO) {
        // 校验站内信编码是否重复
        validateNotifyTemplateCodeDuplicate(null, createReqVO.getCode());

        // 插入
        SystemNotifyTemplate notifyTemplate = NotifyTemplateConvert.INSTANCE.convert(createReqVO);
        SystemNotifyTemplate finalNotifyTemplate = notifyTemplate;
        notifyTemplate = SystemNotifyTemplateDraft.$.produce(notifyTemplate, SystemNotifyTemplate->{
            SystemNotifyTemplate.setParams(parseTemplateContentParams(finalNotifyTemplate.content()));
        });
        notifyTemplate = systemNotifyTemplateRepository.insert(notifyTemplate);

        return notifyTemplate.id();
    }

    @Override
    public void updateNotifyTemplate(NotifyTemplateUpdateReq updateReqVO) {
        // 校验存在
        validateNotifyTemplateExists(updateReqVO.getId());
        // 校验站内信编码是否重复
        validateNotifyTemplateCodeDuplicate(updateReqVO.getId(), updateReqVO.getCode());

        // 更新
        SystemNotifyTemplate updateObj = NotifyTemplateConvert.INSTANCE.convert(updateReqVO);
        SystemNotifyTemplate finalUpdateObj = updateObj;
        updateObj = SystemNotifyTemplateDraft.$.produce(updateObj, SystemNotifyTemplate->{
            SystemNotifyTemplate.setParams(parseTemplateContentParams(finalUpdateObj.content()));
        });
        systemNotifyTemplateRepository.update(updateObj);
    }

    @VisibleForTesting
    public List<String> parseTemplateContentParams(String content) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, content);
    }

    @Override
    public void deleteNotifyTemplate(Long id) {
        // 校验存在
        validateNotifyTemplateExists(id);
        // 删除
        systemNotifyTemplateRepository.deleteById(id);
    }

    private void validateNotifyTemplateExists(Long id) {
        if (!systemNotifyTemplateRepository.findById(id).isPresent()) {
            throw exception(NOTIFY_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public SystemNotifyTemplate getNotifyTemplate(Long id) {
        return systemNotifyTemplateRepository.findById(id).get();
    }

    @Override
    public PageResult<NotifyTemplateResp> getNotifyTemplatePage(NotifyTemplatePageReqVO pageReqVO) {
        Page<SystemNotifyTemplate> postPage = systemNotifyTemplateRepository.selectPage(pageReqVO);
        List<NotifyTemplateResp> listPage = NotifyTemplateConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(listPage, postPage.getTotalElements());
    }

    @VisibleForTesting
    public void validateNotifyTemplateCodeDuplicate(Long id, String code) {
        Optional<SystemNotifyTemplate> opTemplate = systemNotifyTemplateRepository.findByCode(code);
        if (!opTemplate.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw exception(NOTIFY_TEMPLATE_CODE_DUPLICATE, code);
        }
        if (opTemplate.get().id() != id) {
            throw exception(NOTIFY_TEMPLATE_CODE_DUPLICATE, code);
        }
    }

    /**
     * 格式化站内信内容
     *
     * @param content 站内信模板的内容
     * @param params  站内信内容的参数
     * @return 格式化后的内容
     */
    @Override
    public String formatNotifyTemplateContent(String content, Map<String, Object> params) {
        return StrUtil.format(content, params);
    }
}
