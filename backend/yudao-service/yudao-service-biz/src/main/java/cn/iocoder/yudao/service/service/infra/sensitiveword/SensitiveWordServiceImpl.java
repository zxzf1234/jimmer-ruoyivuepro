package cn.iocoder.yudao.service.service.infra.sensitiveword;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.service.convert.infra.sensitiveword.SensitiveWordConvert;
import cn.iocoder.yudao.service.model.infra.sensitiveWord.SystemSensitiveWord;
import cn.iocoder.yudao.service.mq.producer.sensitiveword.SensitiveWordProducer;
import cn.iocoder.yudao.service.repository.infra.sensitiveWord.SystemSensitiveWordRepository;
import cn.iocoder.yudao.service.util.collection.SimpleTrie;
import cn.iocoder.yudao.service.vo.infra.sensitiveword.*;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.filterList;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.SENSITIVE_WORD_EXISTS;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.SENSITIVE_WORD_NOT_EXISTS;

/**
 * 敏感词 Service 实现类
 *
 * @author 永不言败
 */
@Service
@Slf4j
@Validated
public class SensitiveWordServiceImpl implements SensitiveWordService {

    /**
     * 敏感词标签缓存
     * key：敏感词编号 {@link SystemSensitiveWord#id()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Set<String> sensitiveWordTagsCache = Collections.emptySet();

    @Resource
    private SystemSensitiveWordRepository systemSensitiveWordRepository;

    @Resource
    private SensitiveWordProducer sensitiveWordProducer;

    /**
     * 默认的敏感词的字典树，包含所有敏感词
     */
    @Getter
    private volatile SimpleTrie defaultSensitiveWordTrie = new SimpleTrie(Collections.emptySet());
    /**
     * 标签与敏感词的字段数的映射
     */
    @Getter
    private volatile Map<String, SimpleTrie> tagSensitiveWordTries = Collections.emptyMap();

    /**
     * 初始化缓存
     */
    @Override
    @PostConstruct
    public void initLocalCache() {
        // 第一步：查询数据
        List<SystemSensitiveWord> sensitiveWords = systemSensitiveWordRepository.findAll();
        log.info("[initLocalCache][缓存敏感词，数量为:{}]", sensitiveWords.size());

        // 第二步：构建缓存
        // 写入 sensitiveWordTagsCache 缓存
        Set<String> tags = new HashSet<>();
        sensitiveWords.forEach(word -> tags.addAll(word.tags()));
        sensitiveWordTagsCache = tags;
        // 写入 defaultSensitiveWordTrie、tagSensitiveWordTries 缓存
        initSensitiveWordTrie(sensitiveWords);
    }

    private void initSensitiveWordTrie(List<SystemSensitiveWord> wordDOs) {
        // 过滤禁用的敏感词
        wordDOs = filterList(wordDOs, word -> word.status() == CommonStatusEnum.ENABLE.getStatus());

        // 初始化默认的 defaultSensitiveWordTrie
        this.defaultSensitiveWordTrie = new SimpleTrie(CollectionUtils.convertList(wordDOs, SystemSensitiveWord::name));

        // 初始化 tagSensitiveWordTries
        Multimap<String, String> tagWords = HashMultimap.create();
        for (SystemSensitiveWord word : wordDOs) {
            if (CollUtil.isEmpty(word.tags())) {
                continue;
            }
            word.tags().forEach(tag -> tagWords.put(tag, word.name()));
        }
        // 添加到 tagSensitiveWordTries 中
        Map<String, SimpleTrie> tagSensitiveWordTries = new HashMap<>();
        tagWords.asMap().forEach((tag, words) -> tagSensitiveWordTries.put(tag, new SimpleTrie(words)));
        this.tagSensitiveWordTries = tagSensitiveWordTries;
    }

    @Override
    public Long createSensitiveWord(SensitiveWordCreateReqVO createReqVO) {
        // 校验唯一性
        validateSensitiveWordNameUnique(null, createReqVO.getName());

        // 插入
        SystemSensitiveWord sensitiveWord = SensitiveWordConvert.INSTANCE.convert(createReqVO);
        sensitiveWord = systemSensitiveWordRepository.insert(sensitiveWord);
        // 发送消息，刷新缓存
        sensitiveWordProducer.sendSensitiveWordRefreshMessage();
        return sensitiveWord.id();
    }

    @Override
    public void updateSensitiveWord(SensitiveWordUpdateReqVO updateReqVO) {
        // 校验唯一性
        validateSensitiveWordExists(updateReqVO.getId());
        validateSensitiveWordNameUnique(updateReqVO.getId(), updateReqVO.getName());

        // 更新
        SystemSensitiveWord updateObj = SensitiveWordConvert.INSTANCE.convert(updateReqVO);
        systemSensitiveWordRepository.update(updateObj);
        // 发送消息，刷新缓存
        sensitiveWordProducer.sendSensitiveWordRefreshMessage();
    }

    @Override
    public void deleteSensitiveWord(Long id) {
        // 校验存在
        validateSensitiveWordExists(id);
        // 删除
        systemSensitiveWordRepository.deleteById(id);
        // 发送消息，刷新缓存
        sensitiveWordProducer.sendSensitiveWordRefreshMessage();
    }

    private void validateSensitiveWordNameUnique(Long id, String name) {
        Optional<SystemSensitiveWord> opWord = systemSensitiveWordRepository.findByName(name);
        if (!opWord.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的敏感词
        if (id == null) {
            throw exception(SENSITIVE_WORD_EXISTS);
        }
        if (opWord.get().id() ==id) {
            throw exception(SENSITIVE_WORD_EXISTS);
        }
    }

    private void validateSensitiveWordExists(Long id) {
        if (!systemSensitiveWordRepository.findById(id).isPresent()) {
            throw exception(SENSITIVE_WORD_NOT_EXISTS);
        }
    }

    @Override
    public SystemSensitiveWord getSensitiveWord(Long id) {
        return systemSensitiveWordRepository.findById(id).get();
    }

    @Override
    public List<SystemSensitiveWord> getSensitiveWordList() {
        return systemSensitiveWordRepository.findAll();
    }

    @Override
    public PageResult<SensitiveWordRespVO> getSensitiveWordPage(SensitiveWordPageReqVO pageReqVO) {
        Page<SystemSensitiveWord> postPage = systemSensitiveWordRepository.selectPage(pageReqVO);
        List<SensitiveWordRespVO> postList = SensitiveWordConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(postList, postPage.getTotalElements());
    }

    @Override
    public List<SystemSensitiveWord> getSensitiveWordList(SensitiveWordExportReqVO exportReqVO) {
        return systemSensitiveWordRepository.selectList(exportReqVO);
    }

    @Override
    public Set<String> getSensitiveWordTagSet() {
        return sensitiveWordTagsCache;
    }

    @Override
    public List<String> validateText(String text, List<String> tags) {
        if (CollUtil.isEmpty(tags)) {
            return defaultSensitiveWordTrie.validate(text);
        }
        // 有标签的情况
        Set<String> result = new HashSet<>();
        tags.forEach(tag -> {
            SimpleTrie trie = tagSensitiveWordTries.get(tag);
            if (trie == null) {
                return;
            }
            result.addAll(trie.validate(text));
        });
        return new ArrayList<>(result);
    }

    @Override
    public boolean isTextValid(String text, List<String> tags) {
        if (CollUtil.isEmpty(tags)) {
            return defaultSensitiveWordTrie.isValid(text);
        }
        // 有标签的情况
        for (String tag : tags) {
            SimpleTrie trie = tagSensitiveWordTries.get(tag);
            if (trie == null) {
                continue;
            }
            if (!trie.isValid(text)) {
                return false;
            }
        }
        return true;
    }

}
