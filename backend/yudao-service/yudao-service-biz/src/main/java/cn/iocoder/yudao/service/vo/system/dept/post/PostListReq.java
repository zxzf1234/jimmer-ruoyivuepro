package cn.iocoder.yudao.service.vo.system.dept.post;

import cn.iocoder.yudao.service.vo.system.dept.baseVO.PostBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 岗位列表 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PostListReq extends PostBase {

    @Schema(description = "岗位名称,模糊匹配", example = "芋道")
    private String name;

    @Schema(description = "展示状态,参见 CommonStatusEnum 枚举类", example = "1")
    private Integer status;

}
