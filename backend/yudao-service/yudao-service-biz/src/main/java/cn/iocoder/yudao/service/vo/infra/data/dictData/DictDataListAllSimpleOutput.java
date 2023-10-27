package cn.iocoder.yudao.service.vo.infra.data.dictData;

import cn.iocoder.yudao.service.vo.infra.data.baseVO.DictDataBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "获得全部字典数据列表")
@Data
public class DictDataListAllSimpleOutput extends DictDataBase {

    @Schema(description = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "用户性别")
    private String dictType;
}