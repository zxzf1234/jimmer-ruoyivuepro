package cn.iocoder.yudao.service.convert.infra.auth;

import cn.iocoder.yudao.service.api.infra.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import cn.iocoder.yudao.service.api.infra.oauth2.dto.OAuth2AccessTokenRespDTO;
import cn.iocoder.yudao.service.vo.infra.oauth2.token.OAuth2AccessTokenRespVO;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2AccessToken;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface OAuth2TokenConvert {

    OAuth2TokenConvert INSTANCE = Mappers.getMapper(OAuth2TokenConvert.class);

    OAuth2AccessTokenCheckRespDTO convert(SystemOauth2AccessToken bean);

    List<OAuth2AccessTokenRespVO> convert(Page<SystemOauth2AccessToken> page);

    OAuth2AccessTokenRespDTO convert2(SystemOauth2AccessToken bean);

}
