package cn.iocoder.yudao.service.convert.system.dept;

import cn.iocoder.yudao.service.vo.system.dept.post.*;
import cn.iocoder.yudao.service.model.system.dept.SystemPost;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface PostConvert {

    PostConvert INSTANCE = Mappers.getMapper(PostConvert.class);

    List<PostSimpleRespVO> convertList02(List<SystemPost> list);

    List<PostResp> convertPage(Page<SystemPost> page);

    PostResp convert(SystemPost id);

    SystemPost convert(PostCreateReq bean);

    SystemPost convert(PostUpdateReq reqVO);

    List<PostExcelVO> convertList03(List<SystemPost> list);

}
