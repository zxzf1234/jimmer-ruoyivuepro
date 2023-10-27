package cn.iocoder.yudao.service.vo.system.dept.dept;

import cn.iocoder.yudao.service.vo.system.dept.baseVO.DeptBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "获取部门精简信息列表")
@Data
public class DeptListAllSimpleOutput extends DeptBase {
    @Schema(description = "部门编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

}