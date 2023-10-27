package cn.iocoder.yudao.service.vo.system.dept.dept;

import cn.iocoder.yudao.service.vo.system.dept.baseVO.DeptBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "创建部门")
@Data
public class DeptCreateInput extends DeptBase {
}