package cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 查询接块列表 Request VO")
@Data
public class InterfaceListReqVO extends PageParam {
    @Schema(description = "名称,模糊匹配", example = "芋道")
    private String name;

    @Schema(description = "模块,精准匹配", example = "芋道")
    private String moduleName;
}
