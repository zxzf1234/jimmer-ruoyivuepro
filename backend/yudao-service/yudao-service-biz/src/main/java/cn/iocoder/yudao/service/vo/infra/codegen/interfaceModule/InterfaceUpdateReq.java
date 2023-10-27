package cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule;

import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.InterfaceBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Schema(description = "管理后台 - 接口更新 response VO")
@Data
public class InterfaceUpdateReq extends InterfaceBase {
    @Schema(description = "编号", required = true, example = "1")
    private UUID id;
}
