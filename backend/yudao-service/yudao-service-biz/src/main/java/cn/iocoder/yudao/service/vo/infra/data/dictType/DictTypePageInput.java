package cn.iocoder.yudao.service.vo.infra.data.dictType;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.*;
import javax.validation.constraints.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "获得字典类型的分页列表")
@Data
public class DictTypePageInput extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "展示状态", example = "1")
    private Integer status;

    @Schema(description = "字典类型,模糊匹配", example = "sys_common_sex")
    private String type;

    @Schema(description = "字典类型名称,模糊匹配", example = "芋道")
    private String name;

}