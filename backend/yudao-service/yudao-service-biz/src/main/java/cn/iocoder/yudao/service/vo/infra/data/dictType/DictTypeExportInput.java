package cn.iocoder.yudao.service.vo.infra.data.dictType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import java.time.LocalDateTime;

@Schema(description = "导出数据类型")
@Data
public class DictTypeExportInput  {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "字典类型名称,模糊匹配", example = "芋道")
    private String name;

    @Schema(description = "展示状态", example = "1")
    private Integer status;

    @Schema(description = "字典类型,模糊匹配", example = "sys_common_sex")
    private String type;

}