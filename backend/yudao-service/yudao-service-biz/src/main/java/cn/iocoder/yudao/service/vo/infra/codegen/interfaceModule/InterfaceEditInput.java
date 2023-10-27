package cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule;

import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.InterfaceBase;
import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.InterfaceParamBase;
import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.InterfaceSubclassBase;
import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.InterfaceValidationBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Schema(description = "管理后台 - 接口创建 response VO")
@Data
public class InterfaceEditInput extends InterfaceBase {
    @Schema(description = "编号", required = true, example = "1")
    private UUID id;

    private List<inputParam> inputParams;

    private List<outputParam> outputParams;

    private List<inputSubclass> inputSubclasses;

    private List<outputSubclass> outputSubclasses;

    @Schema(description = "入参子类")
    @Data
    public static class inputSubclass extends InterfaceSubclassBase {
        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

        @Schema(description = "子类类型 0 入参 1 出参", example = "0")
        private Integer type = 0;

        @Schema(description = "操作类型", required = true, example = "new")
        private String operateType;

        List<subclassParam> subclassParams;
    }

    @Schema(description = "出参子类")
    @Data
    public static class outputSubclass extends InterfaceSubclassBase {
        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

        @Schema(description = "子类类型 0 入参 1 出参", example = "0")
        private Integer type = 1;

        @Schema(description = "操作类型", required = true, example = "new")
        private String operateType;

        List<subclassParam> subclassParams;
    }

    @Schema(description = "入参")
    @Data
    public static class inputParam extends InterfaceParamBase {

        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

        @Schema(description = "字段校验")
        private List<validation> validations;

        @Schema(description = "出参入参 0 入参 1出参", example = "0")
        private Integer inoutType = 0;

        @Schema(description = "父类类型 0 接口 1 子类", example = "0")
        private Integer parentType = 0;

        @Schema(description = "操作类型", required = true, example = "new")
        private String operateType;

    }

    @Schema(description = "出参")
    @Data
    public static class outputParam extends InterfaceParamBase {

        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

        @Schema(description = "字段校验")
        private List<validation> validations;

        @Schema(description = "出参入参 0 入参 1出参", example = "0")
        private Integer inoutType = 1;

        @Schema(description = "父类类型 0 接口 1 子类", example = "0")
        private Integer parentType = 0;

        @Schema(description = "操作类型", required = true, example = "new")
        private String operateType;

    }

    @Schema(description = "子类参数")
    @Data
    public static class subclassParam extends InterfaceParamBase {

        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

        @Schema(description = "字段校验")
        private List<validation> validations;

        @Schema(description = "父类类型 0 接口 1 子类", example = "0")
        private Integer parentType = 1;

        @Schema(description = "操作类型", required = true, example = "new")
        private String operateType;

    }

    @Schema(description = "校验")
    @Data
    public static class validation extends InterfaceValidationBase {

        @Schema(description = "编号", required = true, example = "1")
        private UUID id;

        @Schema(description = "父表类型0 infra_database_column 1 infra_interface_param")
        private Integer parentType = 1;

        @Schema(description = "操作类型", required = true, example = "new")
        private String operateType;

    }
}
