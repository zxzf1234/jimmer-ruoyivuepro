package cn.iocoder.yudao.service.service.system.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.common.util.collection.MapUtils;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.framework.datapermission.core.annotation.DataPermission;
import cn.iocoder.yudao.service.api.system.permission.dto.DeptDataPermissionRespDTO;
import cn.iocoder.yudao.service.enums.system.permission.DataScopeEnum;
import cn.iocoder.yudao.service.model.infra.data.SystemMenu;
import cn.iocoder.yudao.service.model.system.dept.SystemDept;
import cn.iocoder.yudao.service.model.system.permission.*;
import cn.iocoder.yudao.service.repository.system.permission.SystemRoleMenuRepository;
import cn.iocoder.yudao.service.repository.system.permission.SystemUserRoleRepository;
import cn.iocoder.yudao.service.service.infra.data.MenuService;
import cn.iocoder.yudao.service.service.system.dept.DeptService;
import cn.iocoder.yudao.service.service.system.user.UserService;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static java.util.Collections.singleton;

/**
 * 权限 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private SystemRoleMenuRepository systemRoleMenuRepository;
    @Resource
    private SystemUserRoleRepository systemUserRoleRepository;

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private DeptService deptService;
    @Resource
    private UserService userService;


    @Override
    public List<SystemMenu> getRoleMenuList(Collection<Long> roleIds, Collection<Integer> menuTypes,
                                                     Collection<Integer> menusStatuses) {
        // 任一一个参数为空时，不返回任何菜单
        if (CollectionUtils.isAnyEmpty(roleIds, menuTypes, menusStatuses)) {
            return Collections.emptyList();
        }

        // 判断角色是否包含超级管理员。如果是超级管理员，获取到全部
        List<SystemRole> roleList = roleService.getRoleListFrom(roleIds);
        if (roleService.hasAnySuperAdmin(roleList)) {
            return menuService.getMenuList(menuTypes, menusStatuses);
        }

        // 获得角色拥有的菜单关联
        List<UUID> menuIds = systemRoleMenuRepository.findByRoleIdIn(roleIds).stream().map(SystemRoleMenu::menuId).collect(Collectors.toList());
        return menuService.getMenuList(menuIds, menuTypes, menusStatuses);
    }

    @Override
    public List<Long> getUserRoleIds(Long userId, Collection<Integer> roleStatuses) {
        List<Long> roleIds = systemUserRoleRepository.findByUserId(userId).stream().map(SystemUserRole::roleId).collect(Collectors.toList());
        // 创建用户的时候没有分配角色，会存在空指针异常
        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        // 过滤角色状态
        if (CollectionUtil.isNotEmpty(roleStatuses)) {
            roleIds.removeIf(roleId -> {
                SystemRole role = roleService.getRoleFrom(roleId);
                return role == null || !roleStatuses.contains(role.status());
            });
        }
        return roleIds;
    }

    @Override
    public Set<UUID> getRoleMenuIds(Long roleId) {
        // 如果是管理员的情况下，获取全部菜单编号
        if (roleService.hasAnySuperAdmin(Collections.singletonList(roleId))) {
            return convertSet(menuService.getMenuList(), SystemMenu::id);
        }
        // 如果是非管理员的情况下，获得拥有的菜单编号
        return convertSet(systemRoleMenuRepository.findByRoleId(roleId), SystemRoleMenu::menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoleMenu(Long roleId, Set<UUID> menuIds) {
        // 获得角色拥有菜单编号
        Set<UUID> dbMenuIds = convertSet(systemRoleMenuRepository.findByRoleId(roleId),
                SystemRoleMenu::menuId);
        // 计算新增和删除的菜单编号
        Collection<UUID> createMenuIds = CollUtil.subtract(menuIds, dbMenuIds);
        Collection<UUID> deleteMenuIds = CollUtil.subtract(dbMenuIds, menuIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollectionUtil.isEmpty(createMenuIds)) {
            systemRoleMenuRepository.insertBatch(CollectionUtils.convertList(createMenuIds, menuId -> {
                SystemRoleMenu roleMenu = SystemRoleMenuDraft.$.produce(SystemRoleMenu->{
                    SystemRoleMenu.setRoleId(roleId).setMenuId(menuId);
                });
                return roleMenu;
            }));
        }
        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            systemRoleMenuRepository.deleteByRoleIdAndMenuIdIn(roleId, deleteMenuIds);
        }
    }

    @Override
    public Set<Long> getUserRoleIdListByUserId(Long userId) {
        return convertSet(systemUserRoleRepository.findByUserId(userId),
                SystemUserRole::roleId);
    }

    @Override
    public Set<Long> getUserRoleIdListByRoleIds(Collection<Long> roleIds) {
        return convertSet(systemUserRoleRepository.findByRoleIdIn(roleIds),
                SystemUserRole::userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignUserRole(Long userId, Set<Long> roleIds) {
        // 获得角色拥有角色编号
        Set<Long> dbRoleIds = convertSet(systemUserRoleRepository.findByUserId(userId),
                SystemUserRole::roleId);
        // 计算新增和删除的角色编号
        Collection<Long> createRoleIds = CollUtil.subtract(roleIds, dbRoleIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbRoleIds, roleIds);
        // 执行新增和删除。对于已经授权的角色，不用做任何处理
        if (!CollectionUtil.isEmpty(createRoleIds)) {
            systemUserRoleRepository.insertBatch(CollectionUtils.convertList(createRoleIds, roleId -> {
                SystemUserRole entity = SystemUserRoleDraft.$.produce(SystemUserRole->{
                    SystemUserRole.setUserId(userId).setRoleId(roleId);
                });
                return entity;
            }));
        }
        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            systemUserRoleRepository.deleteByUserIdAndRoleIdIn(userId, deleteMenuIds);
        }
    }

    @Override
    public void assignRoleDataScope(Long roleId, Integer dataScope, List<Long> dataScopeDeptIds) {
        roleService.updateRoleDataScope(roleId, dataScope, dataScopeDeptIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processRoleDeleted(Long roleId) {
        // 标记删除 UserRole
        systemUserRoleRepository.deleteByRoleId(roleId);
        // 标记删除 RoleMenu
        systemRoleMenuRepository.deleteByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processMenuDeleted(UUID menuId) {
        systemRoleMenuRepository.deleteByMenuId(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processUserDeleted(Long userId) {
        systemUserRoleRepository.deleteByUserId(userId);
    }

    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(permissions)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        List<Long> roleIds = getUserRoleIds(userId, singleton(CommonStatusEnum.ENABLE.getStatus()));
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }
        // 判断是否是超管。如果是，当然符合条件
        if (roleService.hasAnySuperAdmin(roleIds)) {
            return true;
        }

        // 遍历权限，判断是否有一个满足
        return Arrays.stream(permissions).anyMatch(permission -> {
            List<SystemMenu> menuList = menuService.getMenuListByPermissionFromCache(permission);
            // 采用严格模式，如果权限找不到对应的 Menu 的话，认为
            if (CollUtil.isEmpty(menuList)) {
                return false;
            }
            // 获得是否拥有该权限，任一一个
            return menuList.stream().anyMatch(menu -> CollUtil.containsAny(roleIds,
                    systemRoleMenuRepository.findByMenuId(menu.id()).stream().map(SystemRoleMenu::roleId).collect(Collectors.toList())));
        });
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        // 如果为空，说明已经有权限
        if (ArrayUtil.isEmpty(roles)) {
            return true;
        }

        // 获得当前登录的角色。如果为空，说明没有权限
        List<Long> roleIds = getUserRoleIds(userId, singleton(CommonStatusEnum.ENABLE.getStatus()));
        if (CollUtil.isEmpty(roleIds)) {
            return false;
        }
        // 判断是否是超管。如果是，当然符合条件
        if (roleService.hasAnySuperAdmin(roleIds)) {
            return true;
        }
        Set<String> userRoles = convertSet(roleService.getRoleListFrom(roleIds),
                SystemRole::code);
        return CollUtil.containsAny(userRoles, Sets.newHashSet(roles));
    }

    @Override
    @DataPermission(enable = false) // 关闭数据权限，不然就会出现递归获取数据权限的问题
    public DeptDataPermissionRespDTO getDeptDataPermission(Long userId) {
        // 获得用户的角色
        List<Long> roleIds = getUserRoleIds(userId, singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 如果角色为空，则只能查看自己
        DeptDataPermissionRespDTO result = new DeptDataPermissionRespDTO();
        if (CollUtil.isEmpty(roleIds)) {
            result.setSelf(true);
            return result;
        }
        List<SystemRole> roles = roleService.getRoleListFrom(roleIds);

        // 获得用户的部门编号的缓存，通过 Guava 的 Suppliers 惰性求值，即有且仅有第一次发起 DB 的查询
        Supplier<Long> userDeptIdCache = Suppliers.memoize(() -> userService.getUser(userId).get().deptId());
        // 遍历每个角色，计算
        for (SystemRole role : roles) {
            // 为空时，跳过
            if (role.dataScope() == 0) {
                continue;
            }
            // 情况一，ALL
            if (Objects.equals(role.dataScope(), DataScopeEnum.ALL.getScope())) {
                result.setAll(true);
                continue;
            }
            // 情况二，DEPT_CUSTOM
            if (Objects.equals(role.dataScope(), DataScopeEnum.DEPT_CUSTOM.getScope())) {
                CollUtil.addAll(result.getDeptIds(), role.dataScopeDeptIds());
                // 自定义可见部门时，保证可以看到自己所在的部门。否则，一些场景下可能会有问题。
                // 例如说，登录时，基于 t_user 的 username 查询会可能被 dept_id 过滤掉
                CollUtil.addAll(result.getDeptIds(), userDeptIdCache.get());
                continue;
            }
            // 情况三，DEPT_ONLY
            if (Objects.equals(role.dataScope(), DataScopeEnum.DEPT_ONLY.getScope())) {
                CollectionUtils.addIfNotNull(result.getDeptIds(), userDeptIdCache.get());
                continue;
            }
            // 情况四，DEPT_DEPT_AND_CHILD
            if (Objects.equals(role.dataScope(), DataScopeEnum.DEPT_AND_CHILD.getScope())) {
                List<SystemDept> depts = deptService.getDeptListByParentId(userDeptIdCache.get(), true);
                CollUtil.addAll(result.getDeptIds(), CollectionUtils.convertList(depts, SystemDept::id));
                // 添加本身部门编号
                CollUtil.addAll(result.getDeptIds(), userDeptIdCache.get());
                continue;
            }
            // 情况五，SELF
            if (Objects.equals(role.dataScope(), DataScopeEnum.SELF.getScope())) {
                result.setSelf(true);
                continue;
            }
            // 未知情况，error log 即可
            log.error("[getDeptDataPermission][LoginUser({}) role({}) 无法处理]", userId, JsonUtils.toJsonString(result));
        }
        return result;
    }

}
