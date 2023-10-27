package cn.iocoder.yudao.service.convert.infra.auth;

import cn.iocoder.yudao.service.vo.infra.oauth2.client.OAuth2ClientCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.oauth2.client.OAuth2ClientRespVO;
import cn.iocoder.yudao.service.vo.infra.oauth2.client.OAuth2ClientUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * OAuth2 客户端 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface OAuth2ClientConvert {

    OAuth2ClientConvert INSTANCE = Mappers.getMapper(OAuth2ClientConvert.class);

    SystemOauth2Client convert(OAuth2ClientCreateReqVO bean);

    SystemOauth2Client convert(OAuth2ClientUpdateReqVO bean);

    OAuth2ClientRespVO convert(SystemOauth2Client bean);

    List<OAuth2ClientRespVO> convertList(List<SystemOauth2Client> list);

    List<OAuth2ClientRespVO> convertPage(Page<SystemOauth2Client> page);

}
