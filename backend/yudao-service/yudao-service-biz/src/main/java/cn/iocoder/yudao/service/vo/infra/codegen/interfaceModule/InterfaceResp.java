package cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule;

import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.InterfaceBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "管理后台 - 接口查询 response VO")
@Data
public class InterfaceResp extends InterfaceBase {
    @Schema(description ="接口id", required = true, example = "1024")
    private UUID id;

    @Schema(description ="接口名", required = true, example = "1024")
    private String moduleName;

    @Schema(description = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;
}
