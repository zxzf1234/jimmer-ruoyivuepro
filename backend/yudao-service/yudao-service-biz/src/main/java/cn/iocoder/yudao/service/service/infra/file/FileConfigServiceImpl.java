package cn.iocoder.yudao.service.service.infra.file;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.framework.common.util.validation.ValidationUtils;
import cn.iocoder.yudao.framework.file.core.client.FileClient;
import cn.iocoder.yudao.framework.file.core.client.FileClientConfig;
import cn.iocoder.yudao.framework.file.core.client.FileClientFactory;
import cn.iocoder.yudao.framework.file.core.enums.FileStorageEnum;
import cn.iocoder.yudao.service.vo.infra.file.config.FileConfigCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.file.config.FileConfigPageReqVO;
import cn.iocoder.yudao.service.vo.infra.file.config.FileConfigUpdateReqVO;
import cn.iocoder.yudao.service.convert.infra.file.FileConfigConvert;
import cn.iocoder.yudao.service.model.infra.file.InfraFileConfig;
import cn.iocoder.yudao.service.model.infra.file.InfraFileConfigDraft;
import cn.iocoder.yudao.service.mq.producer.file.FileConfigProducer;
import cn.iocoder.yudao.service.repository.infra.file.InfraFileConfigRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.FILE_CONFIG_DELETE_FAIL_MASTER;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.FILE_CONFIG_NOT_EXISTS;

/**
 * 文件配置 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class FileConfigServiceImpl implements FileConfigService {

    @Resource
    private FileClientFactory fileClientFactory;
    /**
     * Master FileClient 对象，有且仅有一个，即 {@link InfraFileConfig#master()} 对应的
     */
    @Getter
    private FileClient masterFileClient;

    @Resource
    private InfraFileConfigRepository infraFileConfigRepository;

    @Resource
    private FileConfigProducer fileConfigProducer;

    @Resource
    private Validator validator;

    @Override
    @PostConstruct
    public void initLocalCache() {
        // 第一步：查询数据
        List<InfraFileConfig> configs = infraFileConfigRepository.findAll();
        log.info("[initLocalCache][缓存文件配置，数量为:{}]", configs.size());

        // 第二步：构建缓存：创建或更新文件 Client

        configs.forEach(config -> {

            fileClientFactory.createOrUpdateFileClient(config.id(), config.storage(), config.config());

            // 如果是 master，进行设置
            if (Boolean.TRUE.equals(config.master())) {
                masterFileClient = fileClientFactory.getFileClient(config.id());
            }
        });
    }

    @Override
    public Long createFileConfig(FileConfigCreateReqVO createReqVO) {
        // 插入
        InfraFileConfig fileConfig = FileConfigConvert.INSTANCE.convert(createReqVO);
        fileConfig = InfraFileConfigDraft.$.produce(fileConfig, draft -> {
            draft.setConfig(parseClientConfig(createReqVO.getStorage(), createReqVO.getConfig()))
                    .setMaster(false);
        });
                 // 默认非 master
        infraFileConfigRepository.insert(fileConfig);
        // 发送刷新配置的消息
        fileConfigProducer.sendFileConfigRefreshMessage();
        // 返回
        return fileConfig.id();
    }

    @Override
    public void updateFileConfig(FileConfigUpdateReqVO updateReqVO) {
        // 校验存在
        InfraFileConfig config = validateFileConfigExists(updateReqVO.getId());
        // 更新
        InfraFileConfig updateObj = FileConfigConvert.INSTANCE.convert(updateReqVO);
        updateObj = InfraFileConfigDraft.$.produce(updateObj, draft -> {
            draft.setConfig(parseClientConfig(config.storage(), updateReqVO.getConfig()));
        });

        infraFileConfigRepository.update(updateObj);
        // 发送刷新配置的消息
        fileConfigProducer.sendFileConfigRefreshMessage();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFileConfigMaster(Long id) {
        // 校验存在
        validateFileConfigExists(id);
        // 更新其它为非 master in new InfraFileConfig().setMaster(false)
        infraFileConfigRepository.update(InfraFileConfigDraft.$.produce(draft -> {draft.setMaster(false);}));
        // 更新
        infraFileConfigRepository.update(InfraFileConfigDraft.$.produce(draft -> {draft.setId(id).setMaster(true);}));
        // 发送刷新配置的消息
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                fileConfigProducer.sendFileConfigRefreshMessage();
            }

        });
    }

    private FileClientConfig parseClientConfig(Integer storage, Map<String, Object> config) {
        // 获取配置类
        Class<? extends FileClientConfig> configClass = FileStorageEnum.getByStorage(storage)
                .getConfigClass();
        FileClientConfig clientConfig = JsonUtils.parseObject2(JsonUtils.toJsonString(config), configClass);
        // 参数校验
        ValidationUtils.validate(validator, clientConfig);
        // 设置参数
        return clientConfig;
    }

    @Override
    public void deleteFileConfig(Long id) {
        // 校验存在
        InfraFileConfig config = validateFileConfigExists(id);
        if (Boolean.TRUE.equals(config.master())) {
             throw exception(FILE_CONFIG_DELETE_FAIL_MASTER);
        }
        // 删除
        infraFileConfigRepository.deleteById(id);
        // 发送刷新配置的消息
        fileConfigProducer.sendFileConfigRefreshMessage();
    }

    private InfraFileConfig validateFileConfigExists(Long id) {
        Optional<InfraFileConfig>  opConfig = infraFileConfigRepository.findById(id);
        if (!opConfig.isPresent()) {
            throw exception(FILE_CONFIG_NOT_EXISTS);
        }
        return opConfig.get();
    }

    @Override
    public InfraFileConfig getFileConfig(Long id) {
        return infraFileConfigRepository.findById(id).get();
    }

    @Override
    public PageResult<InfraFileConfig> getFileConfigPage(FileConfigPageReqVO pageReqVO) {
        Page<InfraFileConfig> postPage = infraFileConfigRepository.selectPage(pageReqVO);
        return new PageResult<>(postPage.toList(), postPage.getTotalElements());
    }

    @Override
    public String testFileConfig(Long id) throws Exception {
        // 校验存在
        validateFileConfigExists(id);
        // 上传文件
        byte[] content = ResourceUtil.readBytes("file/erweima.jpg");
        return fileClientFactory.getFileClient(id).upload(content, IdUtil.fastSimpleUUID() + ".jpg", "image/jpeg");
    }

    @Override
    public FileClient getFileClient(Long id) {
        return fileClientFactory.getFileClient(id);
    }

}
