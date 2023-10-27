package cn.iocoder.yudao.service.vo.system.notify.notice;

import cn.iocoder.yudao.service.vo.system.notify.baseVO.NoticeBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 通知公告信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeResp extends NoticeBase {

    @Schema(description = "通知公告序号", required = true, example = "1024")
    private Long id;

    @Schema(description = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

}
