package cn.iocoder.yudao.service.vo.infra.data.dictType;

import cn.iocoder.yudao.service.vo.infra.data.baseVO.DictTypeBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "修改字典类型")
@Data
public class DictTypeUpdateInput extends DictTypeBase {

    @Schema(description = "字典类型编号", example = "1024")
    @NotNull(message = "字典类型编号不能为空")
    private UUID id;

}