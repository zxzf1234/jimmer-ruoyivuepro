package cn.iocoder.yudao.service.vo.system.notify.template;

import cn.iocoder.yudao.service.vo.system.notify.baseVO.NotifyTemplateBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 站内信模版更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NotifyTemplateUpdateReq extends NotifyTemplateBase {

    @Schema(description = "ID", required = true, example = "1024")
    @NotNull(message = "ID 不能为空")
    private Long id;

}
