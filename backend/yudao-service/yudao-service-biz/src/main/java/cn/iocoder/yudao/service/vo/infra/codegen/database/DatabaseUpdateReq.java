package cn.iocoder.yudao.service.vo.infra.codegen.database;

import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class DatabaseUpdateReq extends DatabaseTableBase {
    @Schema(description = "编号", required = true, example = "1")
    private UUID id;

    @Valid // 校验内嵌的字段
    @NotNull(message = "字段定义不能为空")
    private List<Column> columns;

    @Valid // 校验内嵌的字段
    private List<Index> indexes;

    @Valid // 校验内嵌的字段
    private List<mapping> mappings;

    @Schema(description = "更新表字段")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Column extends DatabaseColumnBase {
        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

        @Schema(description = "操作类型", required = true, example = "new")
        private String operateType;

        @Schema(description = "字段校验")
        private List<Validation> validations;
    }

    @Schema(description = "更新表索引")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Index extends DatabaseIndexBase {
        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

        @Schema(description = "操作类型", required = true, example = "new")
        private String operateType;

    }

    @Schema(description = "更新接口字段校验")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Validation extends InterfaceValidationBase {
        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

        @Schema(description = "父表类型0 infra_database_column 1 infra_interface_param")
        private Integer parentType = 0;

        @Schema(description = "操作类型", required = true, example = "new")
        private String operateType;
    }

    @Schema(description = "更新表映射")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class mapping extends DatabaseMappingBase {
        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

        @Schema(description = "操作类型", required = true, example = "new")
        private String operateType;

    }
}
