package cn.iocoder.yudao.module.member.convert.auth;

import cn.iocoder.yudao.module.member.controller.app.auth.vo.*;
import cn.iocoder.yudao.module.member.controller.app.social.vo.AppSocialUserUnbindReqVO;
import cn.iocoder.yudao.service.api.infra.oauth2.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.yudao.service.api.system.sms.dto.code.SmsCodeSendReqDTO;
import cn.iocoder.yudao.service.api.system.sms.dto.code.SmsCodeUseReqDTO;
import cn.iocoder.yudao.service.api.system.social.dto.SocialUserBindReqDTO;
import cn.iocoder.yudao.service.api.system.social.dto.SocialUserUnbindReqDTO;
import cn.iocoder.yudao.service.enums.system.sms.SmsSceneEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    SocialUserBindReqDTO convert(Long userId, Integer userType, AppAuthSocialLoginReqVO reqVO);
    SocialUserUnbindReqDTO convert(Long userId, Integer userType, AppSocialUserUnbindReqVO reqVO);

    SmsCodeSendReqDTO convert(AppAuthSmsSendReqVO reqVO);
    SmsCodeUseReqDTO convert(AppAuthResetPasswordReqVO reqVO, SmsSceneEnum scene, String usedIp);
    SmsCodeUseReqDTO convert(AppAuthSmsLoginReqVO reqVO, Integer scene, String usedIp);

    AppAuthLoginRespVO convert(OAuth2AccessTokenRespDTO bean);

}
