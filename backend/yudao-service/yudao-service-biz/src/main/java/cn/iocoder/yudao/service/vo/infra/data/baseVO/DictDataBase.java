package cn.iocoder.yudao.service.vo.infra.data.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

/**
 * 字典数据表 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DictDataBase {

    @Schema(description = "字典排序", example = "1")
    private Integer sort;

    @Schema(description = "字典标签", example = "管理后台")
    private String label;

    @Schema(description = "字典键值", example = "1")
    private String value;

    @Schema(description = "字典类型", example = "c0fcc1e7-02a0-635b-0702-2c96f6482dae")
    private UUID typeId;

    @Schema(description = "状态（0正常 1停用）", example = "0")
    private Integer status;

    @Schema(description = "颜色类型")
    private String colorType;

    @Schema(description = "css 样式")
    private String cssClass;

    @Schema(description = "备注")
    private String remark;

}
