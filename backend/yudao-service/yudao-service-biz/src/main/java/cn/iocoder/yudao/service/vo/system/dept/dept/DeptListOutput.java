package cn.iocoder.yudao.service.vo.system.dept.dept;

import cn.iocoder.yudao.service.vo.system.dept.baseVO.DeptBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "获取部门列表")
@Data
public class DeptListOutput extends DeptBase {
    @Schema(description = "部门编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "状态,参见 CommonStatusEnum 枚举类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "时间戳格式")
    private LocalDateTime createTime;

}