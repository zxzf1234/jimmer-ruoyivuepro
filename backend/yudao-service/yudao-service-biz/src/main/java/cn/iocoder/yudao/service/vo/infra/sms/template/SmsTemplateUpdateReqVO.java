package cn.iocoder.yudao.service.vo.infra.sms.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 短信模板更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SmsTemplateUpdateReqVO extends SmsTemplateBaseVO {

    @Schema(description = "编号", required = true, example = "1024")
    @NotNull(message = "编号不能为空")
    private Long id;

}
