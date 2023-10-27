package cn.iocoder.yudao.service.repository.system.permission;

import cn.iocoder.yudao.service.vo.infra.permission.role.RoleExportReqVO;
import cn.iocoder.yudao.service.vo.infra.permission.role.RolePageReqVO;
import cn.iocoder.yudao.service.model.system.permission.SystemRole;
import cn.iocoder.yudao.service.model.system.permission.SystemRoleTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SystemRoleRepository extends JRepository<SystemRole, Long> {
    SystemRoleTable systemRoleTable = SystemRoleTable.$;

    default List<SystemRole> selectList(RoleExportReqVO reqVO){
        return sql()
                .createQuery(systemRoleTable)
                .whereIf(reqVO.getStatus() != null, systemRoleTable.status().eq(reqVO.getStatus()))
                .whereIf(StringUtils.hasText(reqVO.getCode()), systemRoleTable.code().eq(reqVO.getCode()))
                .whereIf(StringUtils.hasText(reqVO.getName()), systemRoleTable.name().eq(reqVO.getName()))
                .whereIf(reqVO.getCreateTime() != null, ()-> systemRoleTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                .select(systemRoleTable)
                .execute();
    }

    default Page<SystemRole> pageSelect(RolePageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemRoleTable)
                        .whereIf(reqVO.getStatus() != null, systemRoleTable.status().eq(reqVO.getStatus()))
                        .whereIf(StringUtils.hasText(reqVO.getCode()), systemRoleTable.code().eq(reqVO.getCode()))
                        .whereIf(StringUtils.hasText(reqVO.getName()), systemRoleTable.name().eq(reqVO.getName()))
                        .whereIf(reqVO.getCreateTime() != null, ()-> systemRoleTable.createTime().between(reqVO.getCreateTime()[0], reqVO.getCreateTime()[1]))
                        .select(systemRoleTable)
        );
    }

    List<SystemRole> findByStatusIn(Collection<Integer> statuses);

    List<SystemRole> findByIdIn(Collection<Long> ids);

    Optional<SystemRole> findByCode(String code);

    Optional<SystemRole> findByName(String name);
}
