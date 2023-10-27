package cn.iocoder.yudao.service.service.infra.config;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.config.ConfigCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.config.ConfigExportReqVO;
import cn.iocoder.yudao.service.vo.infra.config.ConfigPageReqVO;
import cn.iocoder.yudao.service.vo.infra.config.ConfigUpdateReqVO;
import cn.iocoder.yudao.service.convert.infra.config.ConfigConvert;

import cn.iocoder.yudao.service.enums.config.ConfigTypeEnum;
import cn.iocoder.yudao.service.model.infra.config.InfraConfig;
import cn.iocoder.yudao.service.model.infra.config.InfraConfigDraft;
import cn.iocoder.yudao.service.repository.infra.config.InfraConfigRepository;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.*;

/**
 * 参数配置 Service 实现类
 */
@Service
@Slf4j
@Validated
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private InfraConfigRepository infraConfigRepository;

    @Override
    public Long createConfig(ConfigCreateReqVO reqVO) {
        // 校验正确性
        validateConfigForCreateOrUpdate(null, reqVO.getKey());
        // 插入参数配置
        InfraConfig config = ConfigConvert.INSTANCE.convert(reqVO);
        config = InfraConfigDraft.$.produce(config, draft -> {
            draft.setType(ConfigTypeEnum.CUSTOM.getType());
        });
        config = infraConfigRepository.insert(config);
        return config.id();
    }

    @Override
    public void updateConfig(ConfigUpdateReqVO reqVO) {
        // 校验正确性
        validateConfigForCreateOrUpdate(reqVO.getId(), null); // 不允许更新 key
        // 更新参数配置
        InfraConfig updateObj = ConfigConvert.INSTANCE.convert(reqVO);
        infraConfigRepository.update(updateObj);
    }

    @Override
    public void deleteConfig(Long id) {
        // 校验配置存在
        InfraConfig config = validateConfigExists(id);
        // 内置配置，不允许删除
        if (ConfigTypeEnum.SYSTEM.getType().equals(config.type())) {
            throw exception(CONFIG_CAN_NOT_DELETE_SYSTEM_TYPE);
        }
        // 删除
        infraConfigRepository.deleteById(id);
    }

    @Override
    public InfraConfig getConfig(Long id) {
        return infraConfigRepository.findById(id).get();
    }

    @Override
    public InfraConfig getConfigByKey(String key) {
        return infraConfigRepository.findByConfigKey(key).get();
    }

    @Override
    public PageResult<InfraConfig> getConfigPage(ConfigPageReqVO reqVO) {
        Page<InfraConfig> postPage = infraConfigRepository.selectPage(reqVO);
        return new PageResult<>(postPage.toList(), postPage.getTotalElements());
    }

    @Override
    public List<InfraConfig> getConfigList(ConfigExportReqVO reqVO) {
        return infraConfigRepository.selectList(reqVO);
    }

    private void validateConfigForCreateOrUpdate(Long id, String key) {
        // 校验自己存在
        validateConfigExists(id);
        // 校验参数配置 key 的唯一性
        if (StrUtil.isNotEmpty(key)) {
            validateConfigKeyUnique(id, key);
        }
    }

    @VisibleForTesting
    public InfraConfig validateConfigExists(Long id) {
        if (id == null) {
            return null;
        }
        Optional<InfraConfig> opConfig = infraConfigRepository.findById(id);
        if (!opConfig.isPresent()) {
            throw exception(CONFIG_NOT_EXISTS);
        }
        return opConfig.get();
    }

    @VisibleForTesting
    public void validateConfigKeyUnique(Long id, String key) {
       Optional<InfraConfig> opConfig = infraConfigRepository.findByConfigKey(key);
        if (!opConfig.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的参数配置
        if (id == null) {
            throw exception(CONFIG_KEY_DUPLICATE);
        }
        if (opConfig.get().id() != id) {
            throw exception(CONFIG_KEY_DUPLICATE);
        }
    }

}
