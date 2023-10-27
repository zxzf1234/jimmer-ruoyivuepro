package cn.iocoder.yudao.service.vo.infra.codegen.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 接口子类 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InterfaceSubclassBase {

    @Schema(description = "接口ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "362331")
    private UUID parentId;

    @Schema(description = "子类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "column")
    private String name;

    @Schema(description = "描述", example = "接口类")
    private String comment;

    @Schema(description = "继承类", example = "baseVO")
    private String inheritClass;

    @Schema(description = "继承类型2 VO类 3 子类", example = "0")
    private Integer inheritType;

    @Schema(description = "子类类型 0 入参 1 出参", example = "0")
    private Integer type;

}
