package cn.iocoder.yudao.service.controller.admin.system.dept;

import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListAllSimpleOutput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListOutput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListInput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptUpdateInput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptCreateInput;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.service.vo.system.dept.dept.*;
import cn.iocoder.yudao.service.convert.system.dept.DeptConvert;
import cn.iocoder.yudao.service.model.system.dept.SystemDept;
import cn.iocoder.yudao.service.service.system.dept.DeptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 部门")
@RestController
@RequestMapping("/system/dept")
@Validated
public class DeptController {

    @Resource
    private DeptService deptService;

    @GetMapping("/get")
    @Operation(summary = "获得部门信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<DeptResp> getDept(@RequestParam("id") Long id) {
        return success(DeptConvert.INSTANCE.convert(deptService.getDept(id)));
    }

    @PostMapping("/create")
    @Operation(summary = "创建部门")
    @PreAuthorize("@ss.hasPermission('system:dept:create')")
    public CommonResult<Long> create(@Valid @RequestBody DeptCreateInput inputVO) {
        return success(deptService.create(inputVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新部门")
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody DeptUpdateInput inputVO) {
        return success(deptService.update(inputVO));
    }
    @DeleteMapping("/delete")
    @Operation(summary = "删除部门")
    @PreAuthorize("@ss.hasPermission('system:dept:delete')")
    @Parameter(name = "id", description = "部门编号", required = true, example = "1024")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        return success(deptService.delete(id));
    }

    @GetMapping("/list")
    @Operation(summary = "获取部门列表")
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public CommonResult<List<DeptListOutput>> list(@Valid DeptListInput inputVO) {
        return success(deptService.list(inputVO));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取部门精简信息列表")
    public CommonResult<List<DeptListAllSimpleOutput>> listAllSimple() {
        return success(deptService.listAllSimple());
    }

}
