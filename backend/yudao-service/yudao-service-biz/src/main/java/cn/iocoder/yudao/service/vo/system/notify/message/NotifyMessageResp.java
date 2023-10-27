package cn.iocoder.yudao.service.vo.system.notify.message;

import cn.iocoder.yudao.service.vo.system.notify.baseVO.NotifyMessageBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;

@Schema(description = "管理后台 - 站内信 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NotifyMessageResp extends NotifyMessageBase {

    @Schema(description = "ID", required = true, example = "1024")
    private Long id;

    @Schema(description = "创建时间", required = true)
    private Date createTime;

}
