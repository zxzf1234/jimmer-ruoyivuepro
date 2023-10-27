package cn.iocoder.yudao.service.service.infra.db;

import cn.iocoder.yudao.service.vo.infra.db.DataSourceConfigCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.db.DataSourceConfigUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.db.InfraDataSourceConfig;

import javax.validation.Valid;
import java.util.List;

/**
 * 数据源配置 Service 接口
 *
 * @author 芋道源码
 */
public interface DataSourceConfigService {

    /**
     * 创建数据源配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDataSourceConfig(@Valid DataSourceConfigCreateReqVO createReqVO);

    /**
     * 更新数据源配置
     *
     * @param updateReqVO 更新信息
     */
    void updateDataSourceConfig(@Valid DataSourceConfigUpdateReqVO updateReqVO);

    /**
     * 删除数据源配置
     *
     * @param id 编号
     */
    void deleteDataSourceConfig(Long id);

    /**
     * 获得数据源配置
     *
     * @param id 编号
     * @return 数据源配置
     */
    InfraDataSourceConfig getDataSourceConfig(Long id);

    /**
     * 获得数据源配置列表
     *
     * @return 数据源配置列表
     */
    List<InfraDataSourceConfig> getDataSourceConfigList();

}
