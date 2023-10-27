package cn.iocoder.yudao.service.vo.infra.data.dictData;

import cn.iocoder.yudao.service.vo.infra.data.baseVO.DictDataBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "修改字典数据")
@Data
public class DictDataUpdateInput extends DictDataBase {

    @Schema(description = "字典数据编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "字典数据编号不能为空")
    private UUID id;

}