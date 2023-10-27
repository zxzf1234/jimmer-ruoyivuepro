package cn.iocoder.yudao.service.service.infra.db;

import cn.iocoder.yudao.framework.mybatis.core.util.JdbcUtils;
import cn.iocoder.yudao.service.vo.infra.db.DataSourceConfigCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.db.DataSourceConfigUpdateReqVO;
import cn.iocoder.yudao.service.convert.infra.db.DataSourceConfigConvert;
import cn.iocoder.yudao.service.model.infra.db.InfraDataSourceConfig;
import cn.iocoder.yudao.service.model.infra.db.InfraDataSourceConfigDraft;
import cn.iocoder.yudao.service.repository.infra.db.InfraDataSourceConfigRepository;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.DATA_SOURCE_CONFIG_NOT_EXISTS;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.DATA_SOURCE_CONFIG_NOT_OK;

/**
 * 数据源配置 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DataSourceConfigServiceImpl implements DataSourceConfigService {

    @Resource
    private InfraDataSourceConfigRepository infraDataSourceConfigRepository;

    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    Long ID_MASTER = 0L;

    @Override
    public Long createDataSourceConfig(DataSourceConfigCreateReqVO createReqVO) {
        InfraDataSourceConfig dataSourceConfig = DataSourceConfigConvert.INSTANCE.convert(createReqVO);
        validateConnectionOK(dataSourceConfig);

        // 插入
        dataSourceConfig = infraDataSourceConfigRepository.insert(dataSourceConfig);
        // 返回
        return dataSourceConfig.id();
    }

    @Override
    public void updateDataSourceConfig(DataSourceConfigUpdateReqVO updateReqVO) {
        // 校验存在
        validateDataSourceConfigExists(updateReqVO.getId());
        InfraDataSourceConfig updateObj = DataSourceConfigConvert.INSTANCE.convert(updateReqVO);
        validateConnectionOK(updateObj);

        // 更新
        infraDataSourceConfigRepository.update(updateObj);
    }

    @Override
    public void deleteDataSourceConfig(Long id) {
        // 校验存在
        validateDataSourceConfigExists(id);
        // 删除
        infraDataSourceConfigRepository.deleteById(id);
    }

    private void validateDataSourceConfigExists(Long id) {
        if (!infraDataSourceConfigRepository.findById(id).isPresent()) {
            throw exception(DATA_SOURCE_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public InfraDataSourceConfig getDataSourceConfig(Long id) {
        // 如果 id 为 0，默认为 master 的数据源
        if (Objects.equals(id, ID_MASTER)) {
            return buildMasterDataSourceConfig();
        }
        // 从 DB 中读取
        return infraDataSourceConfigRepository.findById(id).get();
    }

    @Override
    public List<InfraDataSourceConfig> getDataSourceConfigList() {
        List<InfraDataSourceConfig> result = infraDataSourceConfigRepository.findAll();
        // 补充 master 数据源
        result.add(0, buildMasterDataSourceConfig());
        return result;
    }

    private void validateConnectionOK(InfraDataSourceConfig config) {
        boolean success = JdbcUtils.isConnectionOK(config.url(), config.username(), config.password());
        if (!success) {
            throw exception(DATA_SOURCE_CONFIG_NOT_OK);
        }
    }

    private InfraDataSourceConfig buildMasterDataSourceConfig() {
        String primary = dynamicDataSourceProperties.getPrimary();
        DataSourceProperty dataSourceProperty = dynamicDataSourceProperties.getDatasource().get(primary);
        return InfraDataSourceConfigDraft.$.produce(draft -> {
            draft.setId(ID_MASTER).setName(primary)
                    .setUrl(dataSourceProperty.getUrl())
                    .setUsername(dataSourceProperty.getUsername())
                    .setPassword(dataSourceProperty.getPassword())
                    .setCreateTime(LocalDateTime.now());
        });
    }

}
