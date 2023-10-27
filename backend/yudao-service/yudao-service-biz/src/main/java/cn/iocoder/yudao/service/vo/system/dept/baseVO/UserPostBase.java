package cn.iocoder.yudao.service.vo.system.dept.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 用户岗位表 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class UserPostBase {

    @Schema(description = "用户ID", example = "0")
    private Long userId;

    @Schema(description = "岗位ID", example = "0")
    private Long postId;

}
