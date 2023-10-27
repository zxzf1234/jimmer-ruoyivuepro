package cn.iocoder.yudao.service.vo.infra.codegen.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 接口VO类 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InterfaceVoClassBase {

    @Schema(description = "类名", example = "baseVO")
    private String name;

    @Schema(description = "描述", example = "VO类")
    private String comment;

    @Schema(description = "0 基类 1 入参类 2 出参类 3 入参子类 4 出参子类", example = "0")
    private Integer type;

    @Schema(description = "父类ID", example = "23424552")
    private String parentId;

}
