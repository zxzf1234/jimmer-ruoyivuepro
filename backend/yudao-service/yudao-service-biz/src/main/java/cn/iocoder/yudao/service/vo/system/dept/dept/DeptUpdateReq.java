package cn.iocoder.yudao.service.vo.system.dept.dept;

import cn.iocoder.yudao.service.vo.system.dept.baseVO.DeptBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 部门更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptUpdateReq extends DeptBase {

    @Schema(description = "部门编号", required = true, example = "1024")
    @NotNull(message = "部门编号不能为空")
    private Long id;

}
