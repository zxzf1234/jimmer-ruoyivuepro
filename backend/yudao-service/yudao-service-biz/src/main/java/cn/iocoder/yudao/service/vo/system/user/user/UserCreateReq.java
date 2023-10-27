package cn.iocoder.yudao.service.vo.system.user.user;

import cn.iocoder.yudao.service.vo.system.user.baseVO.UserBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Schema(description = "管理后台 - 用户创建 Request VO")
@Data
public class UserCreateReq extends UserBase {

    @Schema(description = "密码", required = true, example = "123456")
    @NotEmpty(message = "密码不能为空")
    @Size(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

}
