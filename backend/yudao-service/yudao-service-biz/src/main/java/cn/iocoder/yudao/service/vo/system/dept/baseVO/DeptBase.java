package cn.iocoder.yudao.service.vo.system.dept.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 部门表 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DeptBase {

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋道")
    @Size(max = 30, message = "部门名称长度不能超过30个字符")
    @NotBlank(message = "部门名称不能为空")
    private String name;

    @Schema(description = "父部门id", example = "1")
    private Long parentId;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "负责人", example = "1")
    private Long leaderUserId;

    @Schema(description = "联系电话", example = "15601691000")
    @Size(max = 11, message = "联系电话长度不能超过11个字符")
    private String phone;

    @Schema(description = "邮箱", example = "yudao@iocoder.cn")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    @Schema(description = "部门状态（0正常 1停用）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态不能为空")
    private Integer status;

}
