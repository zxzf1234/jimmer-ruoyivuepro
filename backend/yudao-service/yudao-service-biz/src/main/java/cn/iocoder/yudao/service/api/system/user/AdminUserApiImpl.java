package cn.iocoder.yudao.service.api.system.user;

import cn.iocoder.yudao.service.api.system.user.dto.AdminUserRespDTO;
import cn.iocoder.yudao.service.convert.system.user.UserConvert;
import cn.iocoder.yudao.service.model.system.user.SystemUser;
import cn.iocoder.yudao.service.service.system.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Admin 用户 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class AdminUserApiImpl implements AdminUserApi {

    @Resource
    private UserService userService;

    @Override
    public AdminUserRespDTO getUser(Long id) {
        Optional<SystemUser> user = userService.getUser(id);
        return UserConvert.INSTANCE.convert4(user.get());
    }

    @Override
    public List<AdminUserRespDTO> getUserList(Collection<Long> ids) {
        List<SystemUser> users = userService.getUserList(ids);
        return UserConvert.INSTANCE.convertListUser(users);
    }

    @Override
    public List<AdminUserRespDTO> getUserListByDeptIds(Collection<Long> deptIds) {
        List<SystemUser> users = userService.getUserListByDeptIds(deptIds);
        return UserConvert.INSTANCE.convertListUser(users);
    }

    @Override
    public List<AdminUserRespDTO> getUsersByPostIds(Collection<Long> postIds) {
        List<SystemUser> users = userService.getUserListByPostIds(postIds);
        return UserConvert.INSTANCE.convertListUser(users);
    }

    @Override
    public void validateUserList(Collection<Long> ids) {
        userService.validateUserList(ids);
    }

}
