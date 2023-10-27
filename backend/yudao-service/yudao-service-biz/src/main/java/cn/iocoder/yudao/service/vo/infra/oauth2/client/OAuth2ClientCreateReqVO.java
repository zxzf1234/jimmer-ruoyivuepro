package cn.iocoder.yudao.service.vo.infra.oauth2.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - OAuth2 客户端创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuth2ClientCreateReqVO extends OAuth2ClientBaseVO {

}
