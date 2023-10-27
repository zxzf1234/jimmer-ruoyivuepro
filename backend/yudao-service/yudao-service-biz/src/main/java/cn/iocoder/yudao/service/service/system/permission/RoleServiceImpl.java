package cn.iocoder.yudao.service.service.system.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.permission.role.RoleCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.permission.role.RoleExportReqVO;
import cn.iocoder.yudao.service.vo.infra.permission.role.RolePageReqVO;
import cn.iocoder.yudao.service.vo.infra.permission.role.RoleUpdateReqVO;
import cn.iocoder.yudao.service.convert.system.permission.RoleConvert;
import cn.iocoder.yudao.service.enums.system.permission.DataScopeEnum;
import cn.iocoder.yudao.service.enums.system.permission.RoleCodeEnum;
import cn.iocoder.yudao.service.enums.system.permission.RoleTypeEnum;
import cn.iocoder.yudao.service.model.system.permission.SystemRole;
import cn.iocoder.yudao.service.model.system.permission.SystemRoleDraft;
import cn.iocoder.yudao.service.repository.system.permission.SystemRoleRepository;
import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.*;

/**
 * 角色 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    private PermissionService permissionService;

    @Resource
    private SystemRoleRepository systemRoleRepository;


    @Override
    @Transactional
    public Long createRole(RoleCreateReqVO reqVO, Integer type) {
        // 校验角色
        validateRoleDuplicate(reqVO.getName(), reqVO.getCode(), null);
        // 插入到数据库
        SystemRole role = RoleConvert.INSTANCE.convert(reqVO);
        role = SystemRoleDraft.$.produce(role, SystemRole->{
            SystemRole
                    .setType(ObjectUtil.defaultIfNull(type, RoleTypeEnum.CUSTOM.getType()))
                    .setStatus(CommonStatusEnum.ENABLE.getStatus())
                    .setDataScope(DataScopeEnum.ALL.getScope()); // 默认可查看所有数据。原因是，可能一些项目不需要项目权限
        });
        role = systemRoleRepository.insert(role);
        // 返回
        return role.id();
    }

    @Override
    public void updateRole(RoleUpdateReqVO reqVO) {
        // 校验是否可以更新
        validateRoleForUpdate(reqVO.getId());
        // 校验角色的唯一字段是否重复
        validateRoleDuplicate(reqVO.getName(), reqVO.getCode(), reqVO.getId());

        // 更新到数据库
        SystemRole updateObj = RoleConvert.INSTANCE.convert(reqVO);
        systemRoleRepository.update(updateObj);
    }

    @Override
    public void updateRoleStatus(Long id, Integer status) {
        // 校验是否可以更新
        validateRoleForUpdate(id);

        // 更新状态
        SystemRole updateObj = SystemRoleDraft.$.produce(SystemRole->{
            SystemRole.setId(id).setStatus(status);
        });
        systemRoleRepository.update(updateObj);
    }

    @Override
    public void updateRoleDataScope(Long id, Integer dataScope, List<Long> dataScopeDeptIds) {
        // 校验是否可以更新
        validateRoleForUpdate(id);

        // 更新数据范围
        SystemRole updateObject = SystemRoleDraft.$.produce(SystemRole->{
            SystemRole
                    .setId(id)
                    .setDataScope(dataScope)
                    .setDataScopeDeptIds(dataScopeDeptIds);
        });
        systemRoleRepository.update(updateObject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        // 校验是否可以更新
        validateRoleForUpdate(id);
        // 标记删除
        systemRoleRepository.deleteById(id);
        // 删除相关数据
        permissionService.processRoleDeleted(id);
    }

    @Override
    public SystemRole getRoleFrom(Long id) {
        return systemRoleRepository.findById(id).get();
    }

    @Override
    public List<SystemRole> getRoleListByStatus(@Nullable Collection<Integer> statuses) {
        if (CollUtil.isEmpty(statuses)) {
    		return systemRoleRepository.findAll();
		}
        return systemRoleRepository.findByStatusIn(statuses);
    }

    @Override
    public List<SystemRole> getRoleListFrom(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return systemRoleRepository.findByIds(ids);
    }

    @Override
    public boolean hasAnySuperAdmin(Collection<SystemRole> roleList) {
        if (CollectionUtil.isEmpty(roleList)) {
            return false;
        }
        return roleList.stream().anyMatch(role -> RoleCodeEnum.isSuperAdmin(role.code()));
    }

    @Override
    public SystemRole getRole(Long id) {
        return systemRoleRepository.findById(id).get();
    }

    @Override
    public PageResult<SystemRole> getRolePage(RolePageReqVO reqVO) {
        Page<SystemRole> postPage = systemRoleRepository.pageSelect(reqVO);
        return new PageResult<>(postPage.toList(), postPage.getTotalElements());
    }

    @Override
    public List<SystemRole> getRoleList(RoleExportReqVO reqVO) {
        return systemRoleRepository.selectList(reqVO);
    }

    /**
     * 校验角色的唯一字段是否重复
     *
     * 1. 是否存在相同名字的角色
     * 2. 是否存在相同编码的角色
     *
     * @param name 角色名字
     * @param code 角色额编码
     * @param id 角色编号
     */
    @VisibleForTesting
    void validateRoleDuplicate(String name, String code, Long id) {
        // 0. 超级管理员，不允许创建
        if (RoleCodeEnum.isSuperAdmin(code)) {
            throw exception(ROLE_ADMIN_CODE_ERROR, code);
        }
        // 1. 该 name 名字被其它角色所使用
        Optional<SystemRole> opRole = systemRoleRepository.findByName(name);
        if (opRole.isPresent() && (id == null || opRole.get().id() != id)) {
            throw exception(ROLE_NAME_DUPLICATE, name);
        }
        // 2. 是否存在相同编码的角色
        if (!StringUtils.hasText(code)) {
            return;
        }
        // 该 code 编码被其它角色所使用
        opRole = systemRoleRepository.findByCode(code);
        if (opRole.isPresent() && (id == null || opRole.get().id() != id)) {
            throw exception(ROLE_CODE_DUPLICATE, code);
        }
    }

    /**
     * 校验角色是否可以被更新
     *
     * @param id 角色编号
     */
    @VisibleForTesting
    void validateRoleForUpdate(Long id) {
        Optional<SystemRole> opSystemRole = systemRoleRepository.findById(id);
        if (!opSystemRole.isPresent()) {
            throw exception(ROLE_NOT_EXISTS);
        }
        // 内置角色，不允许删除
        if (RoleTypeEnum.SYSTEM.getType().equals(opSystemRole.get().type())) {
            throw exception(ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
    }

    @Override
    public void validateRoleList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得角色信息
        List<SystemRole> roles = systemRoleRepository.findByIdIn(ids);
        Map<Long, SystemRole> roleMap = convertMap(roles, SystemRole::id);
        // 校验
        ids.forEach(id -> {
            SystemRole role = roleMap.get(id);
            if (role == null) {
                throw exception(ROLE_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(role.status())) {
                throw exception(ROLE_IS_DISABLE, role.name());
            }
        });
    }
}
