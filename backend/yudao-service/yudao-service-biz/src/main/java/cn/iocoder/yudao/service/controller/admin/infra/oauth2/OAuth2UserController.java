package cn.iocoder.yudao.service.controller.admin.infra.oauth2;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.service.model.system.user.SystemUser;
import cn.iocoder.yudao.service.vo.infra.oauth2.user.OAuth2UserInfoRespVO;
import cn.iocoder.yudao.service.vo.infra.oauth2.user.OAuth2UserUpdateReqVO;
import cn.iocoder.yudao.service.convert.infra.oauth2.OAuth2UserConvert;
import cn.iocoder.yudao.service.model.system.dept.SystemDept;
import cn.iocoder.yudao.service.model.system.dept.SystemPost;
import cn.iocoder.yudao.service.service.system.dept.DeptService;
import cn.iocoder.yudao.service.service.system.dept.PostService;
import cn.iocoder.yudao.service.service.system.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 提供给外部应用调用为主
 *
 * 1. 在 getUserInfo 方法上，添加 @PreAuthorize("@ss.hasScope('user.read')") 注解，声明需要满足 scope = user.read
 * 2. 在 updateUserInfo 方法上，添加 @PreAuthorize("@ss.hasScope('user.write')") 注解，声明需要满足 scope = user.write
 *
 * @author 芋道源码
 */
@Tag(name = "管理后台 - OAuth2.0 用户")
@RestController
@RequestMapping("/infra/oauth2/user")
@Validated
@Slf4j
public class OAuth2UserController {

    @Resource
    private UserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;

    @GetMapping("/get")
    @Operation(summary = "获得用户基本信息")
    @PreAuthorize("@ss.hasScope('user.read')") //
    public CommonResult<OAuth2UserInfoRespVO> getUserInfo() {
        // 获得用户基本信息
        Optional<SystemUser> user = userService.getUser(getLoginUserId());
        OAuth2UserInfoRespVO resp = OAuth2UserConvert.INSTANCE.convert(user.get());
        // 获得部门信息
        if (user.get().deptId() != 0) {
            SystemDept dept = deptService.getDept(user.get().deptId());
            resp.setDept(OAuth2UserConvert.INSTANCE.convert(dept));
        }
        // 获得岗位信息
        if (CollUtil.isNotEmpty(user.get().postIds())) {
            List<SystemPost> posts = postService.getPostList(user.get().postIds());
            resp.setPosts(OAuth2UserConvert.INSTANCE.convertList(posts));
        }
        return success(resp);
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户基本信息")
    @PreAuthorize("@ss.hasScope('user.write')")
    public CommonResult<Boolean> updateUserInfo(@Valid @RequestBody OAuth2UserUpdateReqVO reqVO) {
        // 这里将 UserProfileUpdateReqVO =》UserProfileUpdateReqVO 对象，实现接口的复用。
        // 主要是，AdminUserService 没有自己的 BO 对象，所以复用只能这么做
        userService.updateUserProfile(getLoginUserId(), OAuth2UserConvert.INSTANCE.convert(reqVO));
        return success(true);
    }

}
