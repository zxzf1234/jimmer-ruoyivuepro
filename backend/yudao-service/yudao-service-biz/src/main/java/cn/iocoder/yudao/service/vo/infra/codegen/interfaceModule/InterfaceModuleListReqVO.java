package cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 查询接口模块列表 Request VO")
@Data
public class InterfaceModuleListReqVO {
    @Schema(description = "名称,模糊匹配", example = "芋道")
    private String name;
}
