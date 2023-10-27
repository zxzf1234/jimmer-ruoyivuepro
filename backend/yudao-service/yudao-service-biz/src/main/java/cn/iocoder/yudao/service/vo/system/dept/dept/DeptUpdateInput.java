package cn.iocoder.yudao.service.vo.system.dept.dept;

import cn.iocoder.yudao.service.vo.system.dept.baseVO.DeptBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "更新部门")
@Data
public class DeptUpdateInput extends DeptBase {
    @Schema(description = "部门编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "部门编号不能为空")
    private Long id;

}