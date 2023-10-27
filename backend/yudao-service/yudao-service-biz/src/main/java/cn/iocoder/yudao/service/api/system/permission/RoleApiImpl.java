package cn.iocoder.yudao.service.api.system.permission;

import cn.iocoder.yudao.service.service.system.permission.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 角色 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class RoleApiImpl implements RoleApi {

    @Resource
    private RoleService roleService;

    @Override
    public void validRoleList(Collection<Long> ids) {
        roleService.validateRoleList(ids);
    }
}
