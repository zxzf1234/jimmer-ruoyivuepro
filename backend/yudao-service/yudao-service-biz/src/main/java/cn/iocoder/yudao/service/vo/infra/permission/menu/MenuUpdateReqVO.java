package cn.iocoder.yudao.service.vo.infra.permission.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "管理后台 - 菜单更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuUpdateReqVO extends MenuBaseVO {

    @Schema(description = "菜单编号", required = true, example = "1024")
    @NotNull(message = "菜单编号不能为空")
    private UUID id;

}
