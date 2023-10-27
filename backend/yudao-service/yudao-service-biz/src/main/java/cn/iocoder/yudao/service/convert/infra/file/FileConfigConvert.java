package cn.iocoder.yudao.service.convert.infra.file;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.file.config.FileConfigCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.file.config.FileConfigRespVO;
import cn.iocoder.yudao.service.vo.infra.file.config.FileConfigUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.file.InfraFileConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文件配置 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface FileConfigConvert {

    FileConfigConvert INSTANCE = Mappers.getMapper(FileConfigConvert.class);

    @Mapping(target = "config", ignore = true)
    InfraFileConfig convert(FileConfigCreateReqVO bean);

    @Mapping(target = "config", ignore = true)
    InfraFileConfig convert(FileConfigUpdateReqVO bean);

    FileConfigRespVO convert(InfraFileConfig bean);

    List<FileConfigRespVO> convertList(List<InfraFileConfig> list);

    PageResult<FileConfigRespVO> convertPage(PageResult<InfraFileConfig> page);

}
