package cn.iocoder.yudao.service.vo.system.notify.template;

import cn.iocoder.yudao.service.vo.system.notify.baseVO.NotifyTemplateBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 站内信模版创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NotifyTemplateCreateReq extends NotifyTemplateBase {
}
