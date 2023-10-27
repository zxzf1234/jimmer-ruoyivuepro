package cn.iocoder.yudao.service.service.infra.data;

import cn.iocoder.yudao.service.vo.infra.permission.menu.MenuCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.permission.menu.MenuListReqVO;
import cn.iocoder.yudao.service.vo.infra.permission.menu.MenuUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.data.SystemMenu;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * 菜单 Service 接口
 *
 * @author 芋道源码
 */
public interface MenuService {

    /**
     * 创建菜单
     *
     * @param reqVO 菜单信息
     * @return 创建出来的菜单编号
     */
    UUID createMenu(MenuCreateReqVO reqVO);

    /**
     * 更新菜单
     *
     * @param reqVO 菜单信息
     */
    void updateMenu(MenuUpdateReqVO reqVO);

    /**
     * 删除菜单
     *
     * @param id 菜单编号
     */
    void deleteMenu(UUID id);

    /**
     * 获得所有菜单列表
     *
     * @return 菜单列表
     */
    List<SystemMenu> getMenuList();

    /**
     * 基于租户，筛选菜单列表
     * 注意，如果是系统租户，返回的还是全菜单
     *
     * @param reqVO 筛选条件请求 VO
     * @return 菜单列表
     */
    List<SystemMenu> getMenuListByTenant(MenuListReqVO reqVO);

    /**
     * 筛选菜单列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return 菜单列表
     */
    List<SystemMenu> getMenuList(MenuListReqVO reqVO);

    /**
     * 获得所有菜单，从缓存中
     *
     * 任一参数为空时，则返回为空
     *
     * @param menuTypes 菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @return 菜单列表
     */
    List<SystemMenu> getMenuList(Collection<Integer> menuTypes, Collection<Integer> menusStatuses);

    /**
     * 获得指定编号的菜单数组，从缓存中
     *
     * 任一参数为空时，则返回为空
     *
     * @param menuIds 菜单编号数组
     * @param menuTypes 菜单类型数组
     * @param menusStatuses 菜单状态数组
     * @return 菜单数组
     */
    List<SystemMenu> getMenuList(Collection<UUID> menuIds, Collection<Integer> menuTypes,
                                          Collection<Integer> menusStatuses);

    /**
     * 获得权限对应的菜单数组
     *
     * @param permission 权限标识
     * @return 数组
     */
    List<SystemMenu> getMenuListByPermissionFromCache(String permission);

    /**
     * 获得菜单
     *
     * @param id 菜单编号
     * @return 菜单
     */
    SystemMenu getMenu(UUID id);

}
