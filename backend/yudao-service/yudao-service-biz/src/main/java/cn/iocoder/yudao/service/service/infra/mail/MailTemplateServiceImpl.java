package cn.iocoder.yudao.service.service.infra.mail;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.mail.template.MailTemplateCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.mail.template.MailTemplatePageReqVO;
import cn.iocoder.yudao.service.vo.infra.mail.template.MailTemplateRespVO;
import cn.iocoder.yudao.service.vo.infra.mail.template.MailTemplateUpdateReqVO;
import cn.iocoder.yudao.service.convert.infra.mail.MailTemplateConvert;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailTemplate;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailTemplateDraft;
import cn.iocoder.yudao.service.mq.producer.mail.MailProducer;
import cn.iocoder.yudao.service.repository.infra.mail.SystemMailTemplateRepository;
import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.*;

/**
 * 邮箱模版 Service 实现类
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
@Service
@Validated
@Slf4j
public class MailTemplateServiceImpl implements MailTemplateService {

    /**
     * 正则表达式，匹配 {} 中的变量
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");

    @Resource
    private SystemMailTemplateRepository systemMailTemplateRepository;


    @Override
    public Long createMailTemplate(MailTemplateCreateReqVO createReqVO) {
        // 校验 code 是否唯一
        validateCodeUnique(null, createReqVO.getCode());

        // 插入
        SystemMailTemplate template = MailTemplateConvert.INSTANCE.convert(createReqVO);
        template = SystemMailTemplateDraft.$.produce(template, SystemMailTemplate->{
            SystemMailTemplate.setParams(parseTemplateContentParams(createReqVO.getContent()));
        });
        template = systemMailTemplateRepository.insert(template);
        return template.id();
    }

    @Override
    public void updateMailTemplate(@Valid MailTemplateUpdateReqVO updateReqVO) {
        // 校验是否存在
        validateMailTemplateExists(updateReqVO.getId());
        // 校验 code 是否唯一
        validateCodeUnique(updateReqVO.getId(),updateReqVO.getCode());

        // 更新
        SystemMailTemplate updateObj = MailTemplateConvert.INSTANCE.convert(updateReqVO);
        updateObj = SystemMailTemplateDraft.$.produce(updateObj, SystemMailTemplate->{
            SystemMailTemplate.setParams(parseTemplateContentParams(updateReqVO.getContent()));
        });
        systemMailTemplateRepository.update(updateObj);
    }

    @VisibleForTesting
    public void validateCodeUnique(Long id, String code) {
        Optional<SystemMailTemplate> opTemplate = systemMailTemplateRepository.findByCode(code);
        if (!opTemplate.isPresent()) {
            return;
        }
        // 存在 template 记录的情况下
        if (id == null // 新增时，说明重复
                || opTemplate.get().id() != id) { // 更新时，如果 id 不一致，说明重复
            throw exception(MAIL_TEMPLATE_CODE_EXISTS);
        }
    }

    @Override
    public void deleteMailTemplate(Long id) {
        // 校验是否存在
        validateMailTemplateExists(id);

        // 删除
        systemMailTemplateRepository.deleteById(id);
    }

    private void validateMailTemplateExists(Long id) {
        if (!systemMailTemplateRepository.findById(id).isPresent()) {
            throw exception(MAIL_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public SystemMailTemplate getMailTemplate(Long id) {return systemMailTemplateRepository.findById(id).get();}

    @Override
    public PageResult<MailTemplateRespVO> getMailTemplatePage(MailTemplatePageReqVO pageReqVO) {
        Page<SystemMailTemplate> postPage = systemMailTemplateRepository.selectPage(pageReqVO);
        List<MailTemplateRespVO> listPage = MailTemplateConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(listPage, postPage.getTotalElements());
    }

    @Override
    public List<SystemMailTemplate> getMailTemplateList() {return systemMailTemplateRepository.findAll();}

    @Override
    public SystemMailTemplate getMailTemplateByCode(String code) {
        return systemMailTemplateRepository.findByCode(code).get();
    }

    @Override
    public String formatMailTemplateContent(String content, Map<String, Object> params) {
        return StrUtil.format(content, params);
    }

    @VisibleForTesting
    public List<String> parseTemplateContentParams(String content) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, content);
    }

    @Override
    public long countByAccountId(Long accountId) {
        return systemMailTemplateRepository.countByAccountId(accountId);
    }

}
