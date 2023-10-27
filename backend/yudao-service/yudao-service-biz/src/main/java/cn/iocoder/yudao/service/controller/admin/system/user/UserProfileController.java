package cn.iocoder.yudao.service.controller.admin.system.user;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.datapermission.core.annotation.DataPermission;
import cn.iocoder.yudao.service.model.system.user.SystemUser;
import cn.iocoder.yudao.service.vo.system.user.profile.UserProfileResp;
import cn.iocoder.yudao.service.vo.system.user.profile.UserProfileUpdatePasswordReqVO;
import cn.iocoder.yudao.service.vo.system.user.profile.UserProfileUpdateReqVO;
import cn.iocoder.yudao.service.convert.system.user.UserConvert;
import cn.iocoder.yudao.service.model.system.dept.SystemDept;
import cn.iocoder.yudao.service.model.system.dept.SystemPost;
import cn.iocoder.yudao.service.model.system.permission.SystemRole;
import cn.iocoder.yudao.service.model.infra.social.SystemSocialUser;
import cn.iocoder.yudao.service.service.system.dept.DeptService;
import cn.iocoder.yudao.service.service.system.dept.PostService;
import cn.iocoder.yudao.service.service.system.permission.PermissionService;
import cn.iocoder.yudao.service.service.system.permission.RoleService;
import cn.iocoder.yudao.service.service.infra.social.SocialUserService;
import cn.iocoder.yudao.service.service.system.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.FILE_IS_EMPTY;

@Tag(name = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/system/user/profile")
@Validated
public class UserProfileController {
    @Resource
    private UserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;
    @Resource
    private SocialUserService socialService;

    @GetMapping("/get")
    @Operation(summary = "获得登录用户信息")
    @DataPermission(enable = false) // 关闭数据权限，避免只查看自己时，查询不到部门。
    public CommonResult<UserProfileResp> profile() {
        // 获得用户基本信息
        SystemUser user = userService.getUser(getLoginUserId()).get();
        UserProfileResp resp = UserConvert.INSTANCE.convert03(user);
        // 获得用户角色
        List<SystemRole> userRoles = roleService.getRoleListFrom(permissionService.getUserRoleIdListByUserId(user.id()));
        resp.setRoles(UserConvert.INSTANCE.convertList(userRoles));
        // 获得部门信息
        if (user.deptId() != 0) {
            SystemDept dept = deptService.getDept(user.deptId());
            resp.setDept(UserConvert.INSTANCE.convert02(dept));
        }
        // 获得岗位信息
        if (CollUtil.isNotEmpty(user.postIds())) {
            List<SystemPost> posts = postService.getPostList(user.postIds());
            resp.setPosts(UserConvert.INSTANCE.convertList02(posts));
        }
        // 获得社交用户信息
        List<SystemSocialUser> socialUsers = socialService.getSocialUserList(user.id(), UserTypeEnum.ADMIN.getValue());
        resp.setSocialUsers(UserConvert.INSTANCE.convertList03(socialUsers));
        return success(resp);
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户个人信息")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "修改用户个人密码")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @RequestMapping(value = "/update-avatar", method = {RequestMethod.POST, RequestMethod.PUT}) // 解决 uni-app 不支持 Put 上传文件的问题
    @Operation(summary = "上传用户个人头像")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return success(avatar);
    }

}
