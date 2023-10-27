package cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule;

import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.InterfaceModuleBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "管理后台 - 接口模块查询 response VO")
@Data
public class InterfaceModuleResp extends InterfaceModuleBase {

    @Schema(description = "模块id", required = true, example = "1024")
    private UUID id;

    @Schema(description = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;
}
