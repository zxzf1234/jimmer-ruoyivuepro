package cn.iocoder.yudao.service.vo.infra.codegen.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 数据库表字段 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DatabaseColumnBase {

    @Schema(description = "表编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "2342533242")
    private UUID tableId;

    @Schema(description = "字段名", requiredMode = Schema.RequiredMode.REQUIRED, example = "user_age")
    private String columnName;

    @Schema(description = "字段类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "int(11)")
    private String dataType;

    @Schema(description = "字段描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "年龄")
    private String columnComment;

    @Schema(description = "是否允许为空", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean nullable;

    @Schema(description = "默认值", requiredMode = Schema.RequiredMode.REQUIRED, example = "年龄")
    private String defaultValue;

    @Schema(description = "Java 属性类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "userAge")
    private String javaType;

    @Schema(description = "字典类型", example = "sys_gender")
    private String dictType;

    @Schema(description = "数据示例", example = "1024")
    private String example;

    @Schema(description = "前端必传", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean required;

    @Schema(description = "关联表", requiredMode = Schema.RequiredMode.REQUIRED, example = "system_user")
    private String relatedTable;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sort;

}
