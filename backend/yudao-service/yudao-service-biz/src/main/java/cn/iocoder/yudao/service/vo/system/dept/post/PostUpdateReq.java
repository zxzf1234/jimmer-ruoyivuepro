package cn.iocoder.yudao.service.vo.system.dept.post;

import cn.iocoder.yudao.service.vo.system.dept.baseVO.PostBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 岗位更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PostUpdateReq extends PostBase {

    @Schema(description = "岗位编号", required = true, example = "1024")
    @NotNull(message = "岗位编号不能为空")
    private Long id;

}
