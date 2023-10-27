package cn.iocoder.yudao.service.convert.infra.oauth2;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.common.core.KeyValue;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.service.vo.infra.oauth2.open.OAuth2OpenAccessTokenRespVO;
import cn.iocoder.yudao.service.vo.infra.oauth2.open.OAuth2OpenAuthorizeInfoRespVO;
import cn.iocoder.yudao.service.vo.infra.oauth2.open.OAuth2OpenCheckTokenRespVO;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2AccessToken;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2Approve;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2Client;
import cn.iocoder.yudao.service.util.oauth2.OAuth2Utils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface OAuth2OpenConvert {

    OAuth2OpenConvert INSTANCE = Mappers.getMapper(OAuth2OpenConvert.class);

    default OAuth2OpenAccessTokenRespVO convert(SystemOauth2AccessToken bean) {
        OAuth2OpenAccessTokenRespVO respVO = convert0(bean);
        respVO.setTokenType(SecurityFrameworkUtils.AUTHORIZATION_BEARER.toLowerCase());
        respVO.setExpiresIn(OAuth2Utils.getExpiresIn(bean.expiresTime()));
        respVO.setScope(OAuth2Utils.buildScopeStr(bean.scopes()));
        return respVO;
    }
    OAuth2OpenAccessTokenRespVO convert0(SystemOauth2AccessToken bean);

    default OAuth2OpenCheckTokenRespVO convert2(SystemOauth2AccessToken bean) {
        OAuth2OpenCheckTokenRespVO respVO = convert3(bean);
        respVO.setExp(LocalDateTimeUtil.toEpochMilli(bean.expiresTime()) / 1000L);
        respVO.setUserType(UserTypeEnum.ADMIN.getValue());
        return respVO;
    }
    OAuth2OpenCheckTokenRespVO convert3(SystemOauth2AccessToken bean);

    default OAuth2OpenAuthorizeInfoRespVO convert(SystemOauth2Client client, List<SystemOauth2Approve> approves) {
        // 构建 scopes
        List<KeyValue<String, Boolean>> scopes = new ArrayList<>(client.scopes().size());
        Map<String, SystemOauth2Approve> approveMap = CollectionUtils.convertMap(approves, SystemOauth2Approve::scope);
        client.scopes().forEach(scope -> {
            SystemOauth2Approve approve = approveMap.get(scope);
            scopes.add(new KeyValue<>(scope, approve != null ? approve.approved() : false));
        });
        // 拼接返回
        return new OAuth2OpenAuthorizeInfoRespVO(
                new OAuth2OpenAuthorizeInfoRespVO.Client(client.name(), client.logo()), scopes);
    }

}
