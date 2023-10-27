package cn.iocoder.yudao.service.vo.infra.permission.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "管理后台 - 菜单信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuRespVO extends MenuBaseVO {

    @Schema(description = "菜单编号", required = true, example = "1024")
    private UUID id;

    @Schema(description = "状态,参见 CommonStatusEnum 枚举类", required = true, example = "1")
    private Integer status;

    @Schema(description = "创建时间", required = true, example = "时间戳格式")
    private LocalDateTime createTime;

}
