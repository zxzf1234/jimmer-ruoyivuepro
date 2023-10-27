package cn.iocoder.yudao.service.vo.infra.codegen.database;

import cn.iocoder.yudao.service.vo.infra.codegen.baseVO.DatabaseTableBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Schema(description = "管理后台 - 数据库的表定义 Response VO")
@Data
public class DatabaseTableResp extends DatabaseTableBase {

    @Schema(description = "表id", required = true, example = "1")
    private UUID id;

}
