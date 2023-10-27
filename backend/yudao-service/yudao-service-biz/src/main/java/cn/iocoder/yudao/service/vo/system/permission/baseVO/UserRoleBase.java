package cn.iocoder.yudao.service.vo.system.permission.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 用户和角色关联表 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class UserRoleBase {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "角色ID")
    private Long roleId;

}
