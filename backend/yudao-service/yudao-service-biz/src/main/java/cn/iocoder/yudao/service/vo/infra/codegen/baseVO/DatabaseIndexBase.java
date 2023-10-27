package cn.iocoder.yudao.service.vo.infra.codegen.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 数据库表索引 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DatabaseIndexBase {

    @Schema(description = "表编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3242323423")
    private UUID tableId;

    @Schema(description = "索引类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "INDEX")
    @NotEmpty(message = "索引类型不能为空")
    private String indexType;

    @Schema(description = "索引名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "IX_infra_database_column_name")
    @NotEmpty(message = "索引名称不能为空")
    private String indexName;

    @Schema(description = "字段", requiredMode = Schema.RequiredMode.REQUIRED, example = "name,type")
    @NotEmpty(message = "字段不能为空")
    private List<String> columnNames;

}
