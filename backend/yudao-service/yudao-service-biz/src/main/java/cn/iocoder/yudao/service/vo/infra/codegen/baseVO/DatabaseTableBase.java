package cn.iocoder.yudao.service.vo.infra.codegen.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 数据库表定义 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DatabaseTableBase {

    @Schema(description = "表名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "infra_database")
    @NotNull(message = "表名称不能为空")
    @Pattern(regexp = "^[a-z_]+$", message = "表名称只能是小写英文和_")
    private String name;

    @Schema(description = "表描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "32")
    private String comment;

    @Schema(description = "备注", example = "备注")
    private String remark;

    @Schema(description = "模块名", requiredMode = Schema.RequiredMode.REQUIRED, example = "codegen")
    @NotNull(message = "业务名不能为空")
    @Pattern(regexp = "^[a-z_]+$", message = "表名称只能是小写英文和_")
    private String businessName;

}
