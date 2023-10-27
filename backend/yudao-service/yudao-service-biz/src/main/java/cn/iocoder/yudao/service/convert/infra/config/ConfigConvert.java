package cn.iocoder.yudao.service.convert.infra.config;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.config.ConfigCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.config.ConfigExcelVO;
import cn.iocoder.yudao.service.vo.infra.config.ConfigRespVO;
import cn.iocoder.yudao.service.vo.infra.config.ConfigUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.config.InfraConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ConfigConvert {

    ConfigConvert INSTANCE = Mappers.getMapper(ConfigConvert.class);

    PageResult<ConfigRespVO> convertPage(PageResult<InfraConfig> page);

    @Mapping(source = "configKey", target = "key")
    ConfigRespVO convert(InfraConfig bean);

    @Mapping(source = "key", target = "configKey")
    InfraConfig convert(ConfigCreateReqVO bean);

    InfraConfig convert(ConfigUpdateReqVO bean);

    @Mapping(source = "configKey", target = "key")
    List<ConfigExcelVO> convertList(List<InfraConfig> list);

}
