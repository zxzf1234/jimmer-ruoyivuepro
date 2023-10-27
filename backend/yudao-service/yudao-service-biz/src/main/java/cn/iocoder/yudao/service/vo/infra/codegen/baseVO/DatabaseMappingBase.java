package cn.iocoder.yudao.service.vo.infra.codegen.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 数据库表映射 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DatabaseMappingBase {

    @Schema(description = "表ID", example = "324562")
    private UUID tableId;

    @Schema(description = "映射名", example = "validations")
    private String name;

    @Schema(description = "是否是list", example = "0")
    private Boolean isList;

    @Schema(description = "注解", example = "@Transient(InfraInterfaceValidationResolver.class)")
    private String annotate;

    @Schema(description = "映射表", example = "InfraInterfaceValidation")
    private String mappingTable;

}
