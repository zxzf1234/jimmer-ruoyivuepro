package cn.iocoder.yudao.service.controller.admin.infra.db;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.service.vo.infra.db.DataSourceConfigCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.db.DataSourceConfigRespVO;
import cn.iocoder.yudao.service.vo.infra.db.DataSourceConfigUpdateReqVO;
import cn.iocoder.yudao.service.convert.infra.db.DataSourceConfigConvert;
import cn.iocoder.yudao.service.model.infra.db.InfraDataSourceConfig;
import cn.iocoder.yudao.service.service.infra.db.DataSourceConfigService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 数据源配置")
@RestController
@RequestMapping("/infra/data-source-config")
@Validated
public class DataSourceConfigController {

    @Resource
    private DataSourceConfigService dataSourceConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建数据源配置")
    @PreAuthorize("@ss.hasPermission('infra:data-source-config:create')")
    public CommonResult<Long> createDataSourceConfig(@Valid @RequestBody DataSourceConfigCreateReqVO createReqVO) {
        return success(dataSourceConfigService.createDataSourceConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新数据源配置")
    @PreAuthorize("@ss.hasPermission('infra:data-source-config:update')")
    public CommonResult<Boolean> updateDataSourceConfig(@Valid @RequestBody DataSourceConfigUpdateReqVO updateReqVO) {
        dataSourceConfigService.updateDataSourceConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除数据源配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('infra:data-source-config:delete')")
    public CommonResult<Boolean> deleteDataSourceConfig(@RequestParam("id") Long id) {
        dataSourceConfigService.deleteDataSourceConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得数据源配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:data-source-config:query')")
    public CommonResult<DataSourceConfigRespVO> getDataSourceConfig(@RequestParam("id") Long id) {
        InfraDataSourceConfig dataSourceConfig = dataSourceConfigService.getDataSourceConfig(id);
        return success(DataSourceConfigConvert.INSTANCE.convert(dataSourceConfig));
    }

    @GetMapping("/list")
    @Operation(summary = "获得数据源配置列表")
    @PreAuthorize("@ss.hasPermission('infra:data-source-config:query')")
    public CommonResult<List<DataSourceConfigRespVO>> getDataSourceConfigList() {
        List<InfraDataSourceConfig> list = dataSourceConfigService.getDataSourceConfigList();
        return success(DataSourceConfigConvert.INSTANCE.convertList(list));
    }

}
