package cn.iocoder.yudao.service.vo.system.dept.dept;

import cn.iocoder.yudao.service.vo.system.dept.baseVO.DeptBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 部门信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptResp extends DeptBase {

    @Schema(description = "部门编号", required = true, example = "1024")
    private Long id;

    @Schema(description = "状态,参见 CommonStatusEnum 枚举类", required = true, example = "1")
    private Integer status;

    @Schema(description = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

}
