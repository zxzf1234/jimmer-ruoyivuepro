package cn.iocoder.yudao.service.controller.admin.infra.data;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.service.vo.infra.permission.menu.*;
import cn.iocoder.yudao.service.convert.infra.dict.MenuConvert;
import cn.iocoder.yudao.service.model.infra.data.SystemMenu;
import cn.iocoder.yudao.service.service.infra.data.MenuService;
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
import java.util.UUID;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 菜单")
@RestController
@RequestMapping("/infra/menu")
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("/create")
    @Operation(summary = "创建菜单")
    @PreAuthorize("@ss.hasPermission('infra:menu:create')")
    public CommonResult<UUID> createMenu(@Valid @RequestBody MenuCreateReqVO reqVO) {
        UUID menuId = menuService.createMenu(reqVO);
        return success(menuId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改菜单")
    @PreAuthorize("@ss.hasPermission('infra:menu:update')")
    public CommonResult<Boolean> updateMenu(@Valid @RequestBody MenuUpdateReqVO reqVO) {
        menuService.updateMenu(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除菜单")
    @Parameter(name = "id", description = "角色编号", required= true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:menu:delete')")
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") UUID id) {
        menuService.deleteMenu(id);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "获取菜单列表", description = "用于【菜单管理】界面")
    @PreAuthorize("@ss.hasPermission('infra:menu:query')")
    public CommonResult<List<MenuRespVO>> getMenuList(MenuListReqVO reqVO) {
        List<SystemMenu> list = menuService.getMenuList(reqVO);
        list.sort(Comparator.comparing(SystemMenu::sort));
        return success(MenuConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取菜单精简信息列表", description = "只包含被开启的菜单，用于【角色分配菜单】功能的选项。" +
            "在多租户的场景下，会只返回租户所在套餐有的菜单")
    public CommonResult<List<MenuSimpleRespVO>> getSimpleMenuList() {
        // 获得菜单列表，只要开启状态的
        MenuListReqVO reqVO = new MenuListReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<SystemMenu> list = menuService.getMenuListByTenant(reqVO);
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SystemMenu::sort));
        return success(MenuConvert.INSTANCE.convertList02(list));
    }

    @GetMapping("/get")
    @Operation(summary = "获取菜单信息")
    @PreAuthorize("@ss.hasPermission('infra:menu:query')")
    public CommonResult<MenuRespVO> getMenu(String id) {
        SystemMenu menu = menuService.getMenu(UUID.fromString(id));
        return success(MenuConvert.INSTANCE.convert(menu));
    }

}
