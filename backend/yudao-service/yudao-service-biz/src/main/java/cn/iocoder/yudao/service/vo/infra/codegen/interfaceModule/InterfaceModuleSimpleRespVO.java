package cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Schema(description = "管理后台 - 接口模块简单查询 response VO")
@Data
public class InterfaceModuleSimpleRespVO {
    @Schema(description = "模块ID", required = true, example = "1024")
    private UUID id;

    @Schema(description = "名称", required = true, example = "芋道")
    private String name;

    @Schema(description = "父部门 ID", required = true, example = "1024")
    private String parentId;
}
