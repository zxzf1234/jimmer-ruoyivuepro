package cn.iocoder.yudao.service.vo.infra.data.dictType;

import cn.iocoder.yudao.service.vo.infra.data.baseVO.DictTypeBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "获得全部字典类型列表")
@Data
public class DictTypeListAllSimpleOutput extends DictTypeBase {

    @Schema(description = "字典类型编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private UUID id;
}