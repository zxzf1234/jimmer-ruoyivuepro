package cn.iocoder.yudao.service.repository.system.user;

import cn.iocoder.yudao.service.vo.system.user.user.UserExportReqVO;
import cn.iocoder.yudao.service.vo.system.user.user.UserPageReqVO;
import cn.iocoder.yudao.service.model.system.dept.SystemDeptFetcher;
import cn.iocoder.yudao.service.model.system.dept.SystemUserPostTable;
import cn.iocoder.yudao.service.model.system.user.SystemUser;
import cn.iocoder.yudao.service.model.system.user.SystemUserFetcher;
import cn.iocoder.yudao.service.model.system.user.SystemUserTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SystemUserRepository extends JRepository<SystemUser, Long>{

    SystemUserTable systemUsersTable = SystemUserTable.$;

    default Page<SystemUser> getUserPage(UserPageReqVO reqVO){

        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                .createQuery(systemUsersTable).where(systemUsersTable.deleted().eq(false))
                .whereIf(StringUtils.hasText(reqVO.getUsername()), systemUsersTable.username().like(reqVO.getUsername()))
                .whereIf(StringUtils.hasText(reqVO.getMobile()), systemUsersTable.mobile().like(reqVO.getMobile()))
                .whereIf(reqVO.getStatus() != null, systemUsersTable.status().eq(reqVO.getStatus()))
                .whereIf(reqVO.getCreateTime() != null,  () -> systemUsersTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                .select(systemUsersTable.fetch(SystemUserFetcher.$.allScalarFields().dept(SystemDeptFetcher.$.allScalarFields())))
        );
    }

    default List<SystemUser> getExportUserList(UserExportReqVO reqVO){
        return sql()
                .createQuery(systemUsersTable).where(systemUsersTable.deleted().eq(false))
                .whereIf(StringUtils.hasText(reqVO.getUsername()), systemUsersTable.username().like(reqVO.getUsername()))
                .whereIf(StringUtils.hasText(reqVO.getMobile()), systemUsersTable.mobile().like(reqVO.getMobile()))
                .whereIf(reqVO.getStatus() != null, systemUsersTable.status().eq(reqVO.getStatus()))
                .whereIf(reqVO.getCreateTime() != null,  () -> systemUsersTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                .select(systemUsersTable.fetch(SystemUserFetcher.$.allScalarFields().dept(SystemDeptFetcher.$.allScalarFields().leaderUser(SystemUserFetcher.$.nickname())))).execute();
    }

    default Optional<SystemUser> GetUser(long id){
        return sql()
                .createQuery(systemUsersTable)
                .where(systemUsersTable.id().eq(id))
                .select(systemUsersTable.fetch(SystemUserFetcher.$.allScalarFields().dept(SystemDeptFetcher.$.allScalarFields())))
                .fetchOptional();
    }

    default List<SystemUser> GetUserListByStatus(Integer status){
        return sql().createQuery(systemUsersTable).where(systemUsersTable.status().eq(status)).select(systemUsersTable).execute();
    }

    default void UpdateUserPassword(long id, String password){
        sql().createUpdate(systemUsersTable).set(systemUsersTable.password(), password).where(systemUsersTable.id().eq(id)).execute();
    }

    default void UpdateUserStatus(long id,int status){
        sql().createUpdate(systemUsersTable).set(systemUsersTable.status(), status).where(systemUsersTable.id().eq(id)).execute();
    }

    default void UpdateUserLogin(long id, String loginIp){
        sql().createUpdate(systemUsersTable).set(systemUsersTable.loginIp(), loginIp).set(systemUsersTable.loginDate(), LocalDateTime.now()).where(systemUsersTable.id().eq(id)).execute();
    }

    default void UpdateUserAvatar(long id, String avatar){
        sql().createUpdate(systemUsersTable).set(systemUsersTable.avatar(), avatar).set(systemUsersTable.loginDate(), LocalDateTime.now()).where(systemUsersTable.id().eq(id)).execute();
    }

    default List<SystemUser> getUserListByPostIds(Collection<Long> postIds){
        return sql().createQuery(SystemUserPostTable.$).where(SystemUserPostTable.$.id().in(postIds)).select(SystemUserPostTable.$.user()).execute();
    }

    Optional<SystemUser> findByUsername(String username);

    Optional<SystemUser> findByMobile(String mobile);

    Optional<SystemUser> findByEmail(String email);

    List<SystemUser> findByNickname(String nickname);

    List<SystemUser> findByDeptIdIn(Collection<Long> deptIds);




}
