package cn.iocoder.yudao.service.service.system.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.datapermission.core.util.DataPermissionUtils;
import cn.iocoder.yudao.service.api.infra.file.FileApi;
import cn.iocoder.yudao.service.model.system.user.SystemUser;
import cn.iocoder.yudao.service.vo.system.user.profile.UserProfileUpdatePasswordReqVO;
import cn.iocoder.yudao.service.vo.system.user.profile.UserProfileUpdateReqVO;
import cn.iocoder.yudao.service.vo.system.user.user.*;
import cn.iocoder.yudao.service.convert.system.user.UserConvert;
import cn.iocoder.yudao.service.model.system.dept.SystemUserPost;
import cn.iocoder.yudao.service.model.system.dept.SystemUserPostDraft;
import cn.iocoder.yudao.service.model.system.user.SystemUserDraft;
import cn.iocoder.yudao.service.repository.system.dept.SystemUserPostRepository;
import cn.iocoder.yudao.service.repository.system.user.SystemUserRepository;
import cn.iocoder.yudao.service.service.system.dept.DeptService;
import cn.iocoder.yudao.service.service.system.dept.PostService;
import cn.iocoder.yudao.service.service.system.permission.PermissionService;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.*;

/**
 * 后台用户 Service 实现类
 *
 * @author 芋道源码
 */
@Service("adminUserService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private SystemUserRepository systemUserRepository;

    @Resource
    private SystemUserPostRepository systemUserPostRepository;

    @Value("${sys.user.init-password:yudaoyuanma}")
    private String userInitPassword;

    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private FileApi fileApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateReq reqVO) {
        // 校验正确性
        validateUserForCreateOrUpdate(null, reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(),
                reqVO.getDeptId(), reqVO.getPostIds());
        SystemUser newUserConvert = UserConvert.INSTANCE.convertUser(reqVO);
        String password = passwordEncoder.encode(reqVO.getPassword());
        SystemUser newUser = SystemUserDraft.$.produce(newUserConvert, SystemUsers ->{
            SystemUsers
                    .setStatus(CommonStatusEnum.ENABLE.getStatus())
                    .setPassword(password);
        });
        newUser = systemUserRepository.insert(newUser);
        return newUser.id();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateReq reqVO) {
        // 校验正确性
        validateUserForCreateOrUpdate(reqVO.getId(), reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(),
                reqVO.getDeptId(), reqVO.getPostIds());
        // 更新用户
        SystemUser updateUser = UserConvert.INSTANCE.convertUser(reqVO);
        systemUserRepository.update(updateUser);
        updateUserPost(reqVO, updateUser);
    }

    private void updateUserPost(UserUpdateReq reqVO, SystemUser updateUser) {
        Long userId = reqVO.getId();
        List<Long> dbPostIds = convertList(systemUserPostRepository.findByUserId(userId), SystemUserPost::postId);
        // 计算新增和删除的岗位编号
        List<Long> postIds = updateUser.postIds();
        Collection<Long> createPostIds = CollUtil.subtract(postIds, dbPostIds);
        Collection<Long> deletePostIds = CollUtil.subtract(dbPostIds, postIds);
        // 执行新增和删除。对于已经授权的菜单，不用做任何处理
        if (!CollectionUtil.isEmpty(createPostIds)) {
            systemUserPostRepository.saveAll(convertList(createPostIds,
                    postId -> SystemUserPostDraft.$.produce(SystemUserPost->{
                        SystemUserPost.setUserId(userId).setPostId(postId);
                    }))
            );

        }
        if (!CollectionUtil.isEmpty(deletePostIds)) {
            systemUserPostRepository.deleteByUserIdAndPostIdIn(userId, deletePostIds);
        }
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {
        systemUserRepository.UpdateUserLogin(id, loginIp);
    }

    @Override
    public void updateUserProfile(Long id, UserProfileUpdateReqVO reqVO) {
        // 校验正确性
        validateUserExists(id);
        validateEmailUnique(id, reqVO.getEmail());
        validateMobileUnique(id, reqVO.getMobile());
        // 执行更新
        SystemUser updateUser = UserConvert.INSTANCE.convertUser(reqVO);
        updateUser = SystemUserDraft.$.produce(updateUser, SystemUsers ->{
            SystemUsers
                    .setId(id);
        });
        systemUserRepository.update(updateUser);
    }

    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordReqVO reqVO) {
        // 校验旧密码密码
        validateOldPassword(id, reqVO.getOldPassword());
        systemUserRepository.UpdateUserPassword(id, encodePassword(reqVO.getNewPassword()));
    }

    @Override
    public String updateUserAvatar(Long id, InputStream avatarFile) throws Exception {
        validateUserExists(id);
        // 存储文件
        String avatar = fileApi.createFile(IoUtil.readBytes(avatarFile));
        systemUserRepository.UpdateUserAvatar(id, avatar);
        return avatar;
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        // 校验用户存在
        validateUserExists(id);
        // 更新密码
        systemUserRepository.UpdateUserPassword(id, password);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        // 校验用户存在
        validateUserExists(id);
        // 更新状态
        systemUserRepository.UpdateUserStatus(id, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        // 校验用户存在
        validateUserExists(id);
        // 删除用户
        systemUserRepository.deleteById(id);
        // 删除用户关联数据
        permissionService.processUserDeleted(id);
        // 删除用户岗位
        systemUserPostRepository.deleteByUserId(id);
    }

    @Override
    public Optional<SystemUser> getUserByUsername(String username) {
        return systemUserRepository.findByUsername(username);
    }

    @Override
    public Optional<SystemUser> getUserByMobile(String mobile) {
        return systemUserRepository.findByMobile(mobile);
    }

    @Override
    public Page<SystemUser> getUserPage(UserPageReqVO reqVO) {
        return systemUserRepository.getUserPage(reqVO);
    }

    @Override
    public List<UserExcelVO> getExportUserList(UserExportReqVO reqVO) {
        List<SystemUser> systemUsers = systemUserRepository.getExportUserList(reqVO);
        return UserConvert.INSTANCE.convertExcelListUser(systemUsers);
    }

    @Override
    public Optional<SystemUser> getUser(Long id) {
        return systemUserRepository.GetUser(id);
    }

    @Override
    public List<SystemUser> getUserListByDeptIds(Collection<Long> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return Collections.emptyList();
        }
        return systemUserRepository.findByDeptIdIn(deptIds);
    }

    @Override
    public List<SystemUser> getUserListByPostIds(Collection<Long> postIds) {
        if (CollUtil.isEmpty(postIds)) {
            return Collections.emptyList();
        }
        return systemUserRepository.getUserListByPostIds(postIds);
    }

    @Override
    public List<SystemUser> getUserList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return systemUserRepository.findByIds(ids);
    }

    @Override
    public void validateUserList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得岗位信息
        List<SystemUser> users = systemUserRepository.findByIds(ids);
        Map<Long, SystemUser> userMap = CollectionUtils.convertMap(users, SystemUser::id);
        // 校验
        ids.forEach(id -> {
            SystemUser user = userMap.get(id);
            if (user == null) {
                throw exception(USER_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(user.status())) {
                throw exception(USER_IS_DISABLE, user.nickname());
            }
        });
    }

    @Override
    public List<SystemUser> getUserListByNickname(String nickname) {
        return systemUserRepository.findByNickname(nickname);
    }


    private void validateUserForCreateOrUpdate(Long id, String username, String mobile, String email,
                                              Long deptId, List<Long> postIds) {
        // 关闭数据权限，避免因为没有数据权限，查询不到数据，进而导致唯一校验不正确
        DataPermissionUtils.executeIgnore(() -> {
            // 校验用户存在
            validateUserExists(id);
            // 校验用户名唯一
            validateUsernameUnique(id, username);
            // 校验手机号唯一
            validateMobileUnique(id, mobile);
            // 校验邮箱唯一
            validateEmailUnique(id, email);
            // 校验部门处于开启状态
            deptService.validateDeptList(CollectionUtils.singleton(deptId));
            // 校验岗位处于开启状态
            postService.validatePostList(postIds);
        });
    }

    @VisibleForTesting
    void validateUserExists(Long id) {
        if (id == null) {
            return;
        }
        Optional<SystemUser> opUser = systemUserRepository.findById(id);
        if (!opUser.isPresent()) {
            throw exception(USER_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    void validateUsernameUnique(Long id, String username) {
        if (StrUtil.isBlank(username)) {
            return;
        }
        Optional<SystemUser> opUser = systemUserRepository.findByUsername(username);
        if (!opUser.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_USERNAME_EXISTS);
        }
        if (!(opUser.get().id() == id)) {
            throw exception(USER_USERNAME_EXISTS);
        }
    }

    @VisibleForTesting
    void validateEmailUnique(Long id, String email) {
        if (StrUtil.isBlank(email)) {
            return;
        }
        Optional<SystemUser> opUser = systemUserRepository.findByEmail(email);
        if (!opUser.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_EMAIL_EXISTS);
        }
        if (!(opUser.get().id() == id)) {
            throw exception(USER_EMAIL_EXISTS);
        }
    }

    @VisibleForTesting
    void validateMobileUnique(Long id, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        Optional<SystemUser> opUser = systemUserRepository.findByMobile(mobile);
        if (!opUser.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_MOBILE_EXISTS);
        }
        if (!(opUser.get().id() == id)) {
            throw exception(USER_MOBILE_EXISTS);
        }
    }

    /**
     * 校验旧密码
     * @param id          用户 id
     * @param oldPassword 旧密码
     */
    @VisibleForTesting
    void validateOldPassword(Long id, String oldPassword) {
        Optional<SystemUser> user = systemUserRepository.findById(id);
        if (!user.isPresent()) {
            throw exception(USER_NOT_EXISTS);
        }
        if (!isPasswordMatch(oldPassword, user.get().password())) {
            throw exception(USER_PASSWORD_FAILED);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public UserImportRespVO importUserList(List<UserImportExcelVO> importUsers, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importUsers)) {
            throw exception(USER_IMPORT_LIST_IS_EMPTY);
        }
        UserImportRespVO respVO = UserImportRespVO.builder().createUsernames(new ArrayList<>())
                .updateUsernames(new ArrayList<>()).failureUsernames(new LinkedHashMap<>()).build();
        importUsers.forEach(importUser -> {
            // 校验，判断是否有不符合的原因
            try {
                validateUserForCreateOrUpdate(null, null, importUser.getMobile(), importUser.getEmail(),
                        importUser.getDeptId(), null);
            } catch (ServiceException ex) {
                respVO.getFailureUsernames().put(importUser.getUsername(), ex.getMessage());
                return;
            }
            // 判断如果不存在，在进行插入
            Optional<SystemUser> existUser = systemUserRepository.findByUsername(importUser.getUsername());
            if (!existUser.isPresent()) {
                SystemUser newUserConvert = UserConvert.INSTANCE.convertUser(importUser);
                newUserConvert = SystemUserDraft.$.produce(newUserConvert, SystemUsers ->{
                    SystemUsers.setPassword(userInitPassword);
                });
                systemUserRepository.insert(newUserConvert);
                respVO.getCreateUsernames().add(importUser.getUsername());
                return;
            }
            // 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureUsernames().put(importUser.getUsername(), USER_USERNAME_EXISTS.getMsg());
                return;
            }
            SystemUser updateUser = UserConvert.INSTANCE.convertUser(importUser);
            systemUserRepository.update(updateUser);

            respVO.getUpdateUsernames().add(importUser.getUsername());
        });
        return respVO;
    }

    @Override
    public List<SystemUser> getUserListByStatus(Integer status) {
        return systemUserRepository.GetUserListByStatus(CommonStatusEnum.ENABLE.getStatus());
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 对密码进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
