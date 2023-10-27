package cn.iocoder.yudao.service.controller.admin.infra.codegen;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.service.service.infra.codegen.DatabaseTableService;
import cn.iocoder.yudao.service.vo.infra.codegen.database.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 数据库表管理")
@RestController
@RequestMapping("/infra/codegen/database-table")
@Validated
public class DatabaseTableController {
    @Resource
    private DatabaseTableService databaseTableService;

    @GetMapping("/list")
    @Operation(summary = "查询数据库表")
    public CommonResult<List<DatabaseTableResp>> getDatabaseTableList(
            @Validated DatabaseTableListReqVO list) {
        return success(databaseTableService.getDatabaseTableList(list));
    }

    @GetMapping("/column_list")
    @Operation(summary = "查询数据库表字段")
    public CommonResult<List<DatabaseTableColumnResp>> getColumnList(
            @Validated DatabaseTableListReqVO list) {
        return success(databaseTableService.getColumnList(list));
    }

    @PostMapping("/create")
    @Operation(summary = "新增数据库表")
    public CommonResult<UUID> createTable(@Valid @RequestBody DatabaseUpdateReq reqVO) {
        return success(databaseTableService.createTable(reqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获得数据库表详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<DatabaseTableDetailResp> getDatabaseTable(@RequestParam("id") UUID id) {
        return success(databaseTableService.getDatabaseTable(id));
    }

    @PostMapping("/update")
    @Operation(summary = "更新数据库表")
    public CommonResult<UUID> updateTable(@Valid @RequestBody DatabaseUpdateReq reqVO) {
        return success(databaseTableService.updateTable(reqVO));
    }
}
