package cn.iocoder.yudao.service.vo.infra.permission.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Schema(description = "管理后台 - 赋予角色菜单 Request VO")
@Data
public class PermissionAssignRoleMenuReqVO {

    @Schema(description = "角色编号", required = true, example = "1")
    @NotNull(message = "角色编号不能为空")
    private Long roleId;

    @Schema(description = "菜单编号列表", example = "1,3,5")
    private Set<UUID> menuIds = Collections.emptySet(); // 兜底

}
