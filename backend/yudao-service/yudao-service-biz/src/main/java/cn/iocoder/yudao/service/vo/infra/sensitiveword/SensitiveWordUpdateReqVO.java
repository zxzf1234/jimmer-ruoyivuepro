package cn.iocoder.yudao.service.vo.infra.sensitiveword;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 敏感词更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SensitiveWordUpdateReqVO extends SensitiveWordBaseVO {

    @Schema(description = "编号", required = true, example = "1")
    @NotNull(message = "编号不能为空")
    private Long id;

}
