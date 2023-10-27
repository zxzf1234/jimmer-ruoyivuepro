package cn.iocoder.yudao.service.vo.system.user.user;

import cn.iocoder.yudao.service.vo.system.user.baseVO.UserBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 用户更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserUpdateReq extends UserBase {

    @Schema(description = "用户编号", required = true, example = "1024")
    @NotNull(message = "用户编号不能为空")
    private Long id;

}
