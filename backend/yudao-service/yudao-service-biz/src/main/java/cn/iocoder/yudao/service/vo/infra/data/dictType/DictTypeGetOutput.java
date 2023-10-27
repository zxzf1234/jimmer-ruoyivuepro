package cn.iocoder.yudao.service.vo.infra.data.dictType;

import cn.iocoder.yudao.service.vo.infra.data.baseVO.DictTypeBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import java.time.LocalDateTime;

@Schema(description = "查询字典类型详细")
@Data
public class DictTypeGetOutput extends DictTypeBase {

    @Schema(description = "字典类型编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private UUID id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "时间戳格式")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;
}