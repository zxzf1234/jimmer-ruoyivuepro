package cn.iocoder.yudao.service.vo.system.notify.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 通知公告表 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class NoticeBase {

    @Schema(description = "公告标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "小博主")
    @NotBlank(message = "公告标题不能为空")
    @Size(max = 50, message = "公告标题不能超过50个字符")
    private String title;

    @Schema(description = "公告内容", requiredMode = Schema.RequiredMode.REQUIRED, example = "半生编码")
    private String content;

    @Schema(description = "公告类型（1通知 2公告）", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "公告类型不能为空")
    private Integer type;

    @Schema(description = "公告状态（0正常 1关闭）", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer status;

}
