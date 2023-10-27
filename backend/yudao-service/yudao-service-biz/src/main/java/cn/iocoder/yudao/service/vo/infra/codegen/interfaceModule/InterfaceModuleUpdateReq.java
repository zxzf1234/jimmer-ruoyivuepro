package cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule;

import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.InterfaceModuleBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Schema(description = "管理后台 - 接口模块更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InterfaceModuleUpdateReq extends InterfaceModuleBase {
    @Schema(description = "编号", required = true, example = "1")
    private UUID id;
}
