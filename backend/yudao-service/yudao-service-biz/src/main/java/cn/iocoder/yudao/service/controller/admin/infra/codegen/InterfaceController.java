package cn.iocoder.yudao.service.controller.admin.infra.codegen;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.service.infra.codegen.InterfaceService;
import cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 系统接口")
@RestController
@RequestMapping("/infra/codegen/interface")
@Validated
public class InterfaceController {
    @Resource
    InterfaceService interfaceService;

    @GetMapping("/list")
    @Operation(summary = "获取接口模块列表")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<PageResult<InterfaceResp>> getList(InterfaceListReqVO reqVO) {
        return success(interfaceService.getList(reqVO));
    }

    @PostMapping("/create")
    @Operation(summary = "创建接口模块")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<String> createModule(@Valid @RequestBody InterfaceEditInput reqVO) {
        return success(interfaceService.create(reqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "修改接口模块")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<String> updateModule(@Valid @RequestBody InterfaceEditInput reqVO) {
        return success(interfaceService.update(reqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获取接口信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<InterfaceDetailResp> getInterface(@RequestParam("id") UUID id) {
        return success(interfaceService.getInterface(id));
    }

    @GetMapping("/vo-class/list")
    @Operation(summary = "获取接口模块列表")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<List<InterfaceVoClassOutput>> getVoClassList(InterfaceListReqVO reqVO) {
        return success(interfaceService.getVoClassList(reqVO));
    }

}
