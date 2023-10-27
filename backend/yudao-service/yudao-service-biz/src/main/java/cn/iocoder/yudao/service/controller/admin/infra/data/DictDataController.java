package cn.iocoder.yudao.service.controller.admin.infra.data;

import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataCreateInput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataUpdateInput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataGetOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataPageOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.DictDataPageInput;
import cn.iocoder.yudao.service.vo.infra.data.dictData.*;

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

import cn.iocoder.yudao.service.service.infra.data.DictDataService;

@Tag(name = "字典数据")
@RestController
@RequestMapping("/infra/data/dict-data")
@Validated
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    @PostMapping("/create")
    @Operation(summary = "新增字典数据")
    @PreAuthorize("@ss.hasPermission('infra:dict:create')")
    public CommonResult<String> create(@Valid @RequestBody DictDataCreateInput inputVO) {
        return success(dictDataService.create(inputVO));
    }

    @PutMapping("/update")
    @Operation(summary = "修改字典数据")
    @PreAuthorize("@ss.hasPermission('infra:dict:update')")
    public CommonResult<Boolean> update(@Valid @RequestBody DictDataUpdateInput inputVO) {
        return success(dictDataService.update(inputVO));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除字典数据")
    @PreAuthorize("@ss.hasPermission('infra:dict:delete')")
    @Parameter(name = "id", description = "编号", example = "123423")
    public CommonResult<Boolean> delete(@RequestParam("id") UUID id) {
        return success(dictDataService.delete(id));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获得全部字典数据列表")
    public CommonResult<List<DictDataListAllSimpleOutput>> listAllSimple() {
        return success(dictDataService.listAllSimple());
    }

    @GetMapping("/get")
    @Operation(summary = "查询字典数据详细")
    @PreAuthorize("@ss.hasPermission('infra:dict:query')")
    @Parameter(name = "id", description = "编号", required = true, example = "10243343")
    public CommonResult<DictDataGetOutput> get(@RequestParam("id") UUID id) {
        return success(dictDataService.get(id));
    }

    @GetMapping("/export")
    @Operation(summary = "导出字典数据")
    @PreAuthorize("@ss.hasPermission('infra:dict:export')")
    @Parameter(name = "DictDataExportInput", description = "字典类型导出 Request", example = "")
    public void export(HttpServletResponse response, @Valid DictDataExportInput inputVO) {
        dictDataService.export(response, inputVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得字典类型的分页列表")
    @PreAuthorize("@ss.hasPermission('infra:dict:query')")
    public CommonResult<PageResult<DictDataPageOutput>> page(@Valid DictDataPageInput inputVO) {
        return success(dictDataService.page(inputVO));
    }

}
