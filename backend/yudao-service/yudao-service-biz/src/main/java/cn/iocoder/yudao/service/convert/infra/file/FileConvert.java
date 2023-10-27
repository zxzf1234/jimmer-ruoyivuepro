package cn.iocoder.yudao.service.convert.infra.file;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.file.file.FileRespVO;
import cn.iocoder.yudao.service.model.infra.file.InfraFile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(InfraFile bean);

    PageResult<FileRespVO> convertPage(PageResult<InfraFile> page);

}
