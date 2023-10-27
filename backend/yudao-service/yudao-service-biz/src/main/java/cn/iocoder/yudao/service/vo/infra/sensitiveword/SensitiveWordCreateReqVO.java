package cn.iocoder.yudao.service.vo.infra.sensitiveword;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 敏感词创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SensitiveWordCreateReqVO extends SensitiveWordBaseVO {

}
