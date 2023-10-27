package cn.iocoder.yudao.service.vo.infra.codegen.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 代码生成表校验定义 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InterfaceValidationBase {

    @Schema(description = "父表ID")
    private UUID parentId;

    @Schema(description = "父表类型0 infra_database_column 1 infra_interface_param")
    private Integer parentType;

    @Schema(description = "校验注解", requiredMode = Schema.RequiredMode.REQUIRED, example = "NotBlank")
    @NotBlank(message = "校验注解不能为空")
    private String validation;

    @Schema(description = "校验条件", requiredMode = Schema.RequiredMode.REQUIRED, example = "regexp = \"^[a-zA-Z0-9]{4,30}$\"")
    @NotNull(message = "校验条件不能为空")
    private String validationCondition;

    @Schema(description = "报错信息", requiredMode = Schema.RequiredMode.REQUIRED, example = "用户账号由 数字、字母 组成")
    @NotNull(message = "报错信息不能为空")
    private String message;

}
