package cn.iocoder.yudao.service.vo.system.permission.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 角色信息表 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RoleBase {

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色权限字符串")
    private String code;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private Integer dataScope;

    @Schema(description = "数据范围(指定部门数组)")
    private List<Long> dataScopeDeptIds;

    @Schema(description = "角色状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "角色类型")
    private Integer type;

    @Schema(description = "备注")
    private String remark;

}
