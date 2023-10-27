package cn.iocoder.yudao.service.vo.system.dept.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "获取部门列表")
@Data
public class DeptListInput  {
    @Schema(description = "展示状态,参见 CommonStatusEnum 枚举类", example = "1")
    private Integer status;

    @Schema(description = "部门名称,模糊匹配", example = "芋道")
    private String name;

}