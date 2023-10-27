package cn.iocoder.yudao.service.vo.system.notify.notice;

import cn.iocoder.yudao.service.vo.system.notify.baseVO.NoticeBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 岗位公告更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeUpdateReq extends NoticeBase {

    @Schema(description = "岗位公告编号", required = true, example = "1024")
    @NotNull(message = "岗位公告编号不能为空")
    private Long id;

}
