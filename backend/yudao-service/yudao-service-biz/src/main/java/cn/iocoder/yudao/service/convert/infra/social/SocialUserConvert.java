package cn.iocoder.yudao.service.convert.infra.social;

import cn.iocoder.yudao.service.api.system.social.dto.SocialUserBindReqDTO;
import cn.iocoder.yudao.service.api.system.social.dto.SocialUserUnbindReqDTO;
import cn.iocoder.yudao.service.vo.infra.social.SocialUserBindReqVO;
import cn.iocoder.yudao.service.vo.infra.social.SocialUserUnbindReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SocialUserConvert {

    SocialUserConvert INSTANCE = Mappers.getMapper(SocialUserConvert.class);

    SocialUserBindReqDTO convert(Long userId, Integer userType, SocialUserBindReqVO reqVO);

    SocialUserUnbindReqDTO convert(Long userId, Integer userType, SocialUserUnbindReqVO reqVO);

}
