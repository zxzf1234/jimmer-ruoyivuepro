package cn.iocoder.yudao.service.vo.infra.codegen.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 接口参数 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InterfaceParamBase {

    @Schema(description = "参数名", example = "name")
    private String name;

    @Schema(description = "描述", example = "字段名")
    private String comment;

    @Schema(description = "是否是list", example = "1")
    private Boolean isList;

    @Schema(description = "参数类型", example = "string")
    private String variableType;

    @Schema(description = "关联字段id", example = "23423423")
    private String relatedId;

    @Schema(description = "关联字段类型 1 column_id 2 VO_id 3 SubClass_id", example = "0")
    private Integer relatedType;

    @Schema(description = "示例", example = "示例")
    private String example;

    @Schema(description = "前端必传", example = "1")
    private Boolean required;

    @Schema(description = "父id", example = "''")
    private UUID parentId;

    @Schema(description = "父类类型 0 接口 1 子类", example = "0")
    private Integer parentType;

    @Schema(description = "出参入参 0 入参 1出参", example = "0")
    private Integer inoutType;

}
