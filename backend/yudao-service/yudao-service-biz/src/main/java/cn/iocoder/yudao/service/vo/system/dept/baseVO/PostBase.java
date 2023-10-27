package cn.iocoder.yudao.service.vo.system.dept.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 岗位信息表 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PostBase {

    @Schema(description = "岗位编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "yudao")
    @Size(max = 64, message = "岗位编码长度不能超过64个字符")
    @NotBlank(message = "岗位编码不能为空")
    private String code;

    @Schema(description = "岗位名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "小博主")
    @Size(max = 50, message = "岗位名称长度不能超过50个字符")
    @NotBlank(message = "岗位名称不能为空")
    private String name;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "状态,参见 CommonStatusEnum 枚举类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "备注", example = "快乐的备注")
    private String remark;

}
