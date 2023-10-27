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

@Schema(description = "管理后台 - 数据库表详情")
@Data
public class DatabaseTableDetailResp extends DatabaseTableBase {
    @Schema(description = "编号", required = true, example = "1")
    private UUID id;

    @Valid // 校验内嵌的字段
    @NotNull(message = "字段定义不能为空")
    private List<Column> columns;

    @Valid // 校验内嵌的字段
    private List<Index> indexes;

    @Valid // 校验内嵌的字段
    private List<mapping> mappings;

    @Schema(description = "表字段")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Column extends DatabaseColumnBase {

        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

        @Schema(description = "字段校验")
        private List<Validation> validations;
    }

    @Schema(description = "更新表定义")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Index extends DatabaseIndexBase {

        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

    }

    @Schema(description = "接口字段校验")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class Validation extends InterfaceValidationBase {
        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

    }

    @Schema(description = "表映射")
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class mapping extends DatabaseMappingBase {

        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

    }


}
