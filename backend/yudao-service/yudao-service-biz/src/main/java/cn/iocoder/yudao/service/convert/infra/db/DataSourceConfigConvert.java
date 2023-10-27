package cn.iocoder.yudao.service.convert.infra.db;

import java.util.*;

import cn.iocoder.yudao.service.model.infra.db.InfraDataSourceConfig;
import cn.iocoder.yudao.service.vo.infra.db.DataSourceConfigCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.db.DataSourceConfigRespVO;
import cn.iocoder.yudao.service.vo.infra.db.DataSourceConfigUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据源配置 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface DataSourceConfigConvert {

    DataSourceConfigConvert INSTANCE = Mappers.getMapper(DataSourceConfigConvert.class);

    InfraDataSourceConfig convert(DataSourceConfigCreateReqVO bean);

    InfraDataSourceConfig convert(DataSourceConfigUpdateReqVO bean);

    DataSourceConfigRespVO convert(InfraDataSourceConfig bean);

    List<DataSourceConfigRespVO> convertList(List<InfraDataSourceConfig> list);

}
