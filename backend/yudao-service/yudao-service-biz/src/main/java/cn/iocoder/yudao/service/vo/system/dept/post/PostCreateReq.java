package cn.iocoder.yudao.service.vo.system.dept.post;

import cn.iocoder.yudao.service.vo.system.dept.baseVO.PostBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 岗位创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PostCreateReq extends PostBase {
}
