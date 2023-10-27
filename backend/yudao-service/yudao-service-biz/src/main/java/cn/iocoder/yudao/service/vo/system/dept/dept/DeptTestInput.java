package cn.iocoder.yudao.service.vo.system.dept.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "dd")
@Data
public class DeptTestInput  {

    @Schema(description = "用户ID")
    private Long userId;

}