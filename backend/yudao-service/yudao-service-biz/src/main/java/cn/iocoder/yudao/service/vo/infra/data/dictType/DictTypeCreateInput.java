package cn.iocoder.yudao.service.vo.infra.data.dictType;

import cn.iocoder.yudao.service.vo.infra.data.baseVO.DictTypeBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "创建字典类型")
@Data
public class DictTypeCreateInput extends DictTypeBase {

}