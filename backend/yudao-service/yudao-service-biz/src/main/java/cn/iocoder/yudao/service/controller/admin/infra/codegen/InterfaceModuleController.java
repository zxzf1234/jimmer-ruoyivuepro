package cn.iocoder.yudao.service.controller.admin.infra.codegen;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.service.service.infra.codegen.InterfaceModuleService;
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

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 接口模块")
@RestController
@RequestMapping("/infra/codegen/interface-module")
@Validated
public class InterfaceModuleController {

    @Resource
    InterfaceModuleService interfaceModuleService;

    @GetMapping("/list")
    @Operation(summary = "获取接口模块列表")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<List<InterfaceModuleResp>> getModuleList(InterfaceModuleListReqVO reqVO) {
        return success(interfaceModuleService.getModuleList(reqVO));
    }

    @PostMapping("/create")
    @Operation(summary = "创建接口模块")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<String> createModule(@Valid @RequestBody InterfaceModuleCreateReq reqVO) {
        return success(interfaceModuleService.create(reqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "修改接口模块")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<String> updateModule(@Valid @RequestBody InterfaceModuleUpdateReq reqVO) {
        return success(interfaceModuleService.update(reqVO));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取模块精简信息列表")
    public CommonResult<List<InterfaceModuleSimpleRespVO>> getSimpleModuleList() {
        return success(interfaceModuleService.getSimpleModuleList());
    }

    @GetMapping("/get")
    @Operation(summary = "获取模块精简信息列表")
    public CommonResult<InterfaceModuleResp> getModule(String id) {
        return success(interfaceModuleService.getModule(id));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除模块")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<Boolean> deleteModule(@RequestParam("id") String id) {
        interfaceModuleService.deleteModule(id);
        return success(true);
    }
}
