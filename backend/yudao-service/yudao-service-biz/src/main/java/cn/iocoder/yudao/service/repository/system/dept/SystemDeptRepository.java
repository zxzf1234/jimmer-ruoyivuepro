package cn.iocoder.yudao.service.repository.system.dept;

import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListInput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListReq;
import cn.iocoder.yudao.service.model.system.dept.SystemDept;
import cn.iocoder.yudao.service.model.system.dept.SystemDeptTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public interface SystemDeptRepository extends JRepository<SystemDept, Long> {
    SystemDeptTable systemDeptTable = SystemDeptTable.$;

    default List<SystemDept> selectList(DeptListReq reqVO)
    {
        return sql().
                createQuery(systemDeptTable).
                whereIf(StringUtils.hasText(reqVO.getName()), systemDeptTable.name().like(reqVO.getName())).
                whereIf(reqVO.getStatus() != null, systemDeptTable.status().eq(reqVO.getStatus())).
                select(systemDeptTable).
                execute();
    }

    default List<SystemDept> selectList(DeptListInput reqVO)
    {
        return sql().
                createQuery(systemDeptTable).
                whereIf(StringUtils.hasText(reqVO.getName()), systemDeptTable.name().like(reqVO.getName())).
                whereIf(reqVO.getStatus() != null, systemDeptTable.status().eq(reqVO.getStatus())).
                select(systemDeptTable).
                execute();
    }

    long countByParentId(Long parentId);

    Optional<SystemDept> findByParentIdAndName(Long parentId, String name);
}
