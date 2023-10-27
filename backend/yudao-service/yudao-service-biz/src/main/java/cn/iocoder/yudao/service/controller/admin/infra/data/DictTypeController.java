package cn.iocoder.yudao.service.controller.admin.infra.data;

import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypeExportInput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypeListAllSimpleOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypeGetOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypePageOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypePageInput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypeUpdateInput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypeCreateInput;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.service.service.infra.data.DictTypeService;
import cn.iocoder.yudao.service.vo.infra.data.dictType.*;

@Tag(name = "字典类型")
@RestController
@RequestMapping("/infra/data/dict-type")
@Validated
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建字典类型")
    @PreAuthorize("@ss.hasPermission('infra:dict:create')")
    public CommonResult<UUID> create(@Valid @RequestBody DictTypeCreateInput inputVO) {
        return success(dictTypeService.create(inputVO));
    }

    @PutMapping("/update")
    @Operation(summary = "修改字典类型")
    @PreAuthorize("@ss.hasPermission('infra:dict:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody DictTypeUpdateInput inputVO) {
        return success(dictTypeService.update(inputVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除字典类型")
    @PreAuthorize("@ss.hasPermission('infra:dict:delete')")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<Boolean> delete(@RequestParam("id") UUID id) {
        return success(dictTypeService.delete(id));
    }

    @GetMapping("/page")
    @Operation(summary = "获得字典类型的分页列表")
    @PreAuthorize("@ss.hasPermission('infra:dict:query')")
    public CommonResult<PageResult<DictTypePageOutput>> page(@Valid DictTypePageInput inputVO) {
        return success(dictTypeService.page(inputVO));
    }

    @GetMapping("/get")
    @Operation(summary = "查询字典类型详细")
    @PreAuthorize("@ss.hasPermission('infra:dict:query')")
    @Parameter(name = "id", description = "编号", required = true, example = "434543")
    public CommonResult<DictTypeGetOutput> get(@RequestParam("id") UUID id) {
        return success(dictTypeService.get(id));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获得全部字典类型列表")
    public CommonResult<List<DictTypeListAllSimpleOutput>> listAllSimple() {
        return success(dictTypeService.listAllSimple());
    }

    @GetMapping("/export")
    @Operation(summary = "导出数据类型")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public void export(HttpServletResponse response, @Valid DictTypeExportInput inputVO) {
        dictTypeService.export(response, inputVO);
    }

}
