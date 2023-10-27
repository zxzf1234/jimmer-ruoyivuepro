package cn.iocoder.yudao.service.vo.infra.data.dictData;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "获得字典类型的分页列表")
@Data
public class DictDataPageInput extends PageParam {

    @Schema(description = "展示状态", example = "1")
    private Integer status;

    @Schema(description = "字典标签", example = "芋道")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    private String label;

    @Schema(description = "字典类型,模糊匹配", example = "sys_common_sex")
    @Size(max = 100, message = "字典类型类型长度不能超过100个字符")
    private String dictType;

}