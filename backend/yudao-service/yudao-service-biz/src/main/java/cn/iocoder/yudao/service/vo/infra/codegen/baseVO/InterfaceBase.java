package cn.iocoder.yudao.service.vo.infra.codegen.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 接口 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InterfaceBase {

    @Schema(description = "接口名", example = "get")
    private String name;

    @Schema(description = "描述", example = "获取")
    private String comment;

    @Schema(description = "调用方法", example = "GET")
    private String method;

    @Schema(description = "权限")
    private String authorize;

    @Schema(description = "是否开启事务")
    private Boolean isTransaction;

    @Schema(description = "接口模块")
    private UUID moduleId;

    @Schema(description = "入参类型", example = "void")
    private String inputType;

    @Schema(description = "入参继承类", example = "baseVO")
    private String inputExtendClass;

    @Schema(description = "出参类型", example = "void")
    private String outputType;

    @Schema(description = "出参继承类", example = "baseVO")
    private String outputExtendClass;

    @Schema(description = "入参传HttpServletResponse", example = "0")
    private Boolean inputServlet;

}
