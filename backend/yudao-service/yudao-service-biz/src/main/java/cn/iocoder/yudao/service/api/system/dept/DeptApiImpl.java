package cn.iocoder.yudao.service.api.system.dept;

import cn.iocoder.yudao.service.api.system.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.service.convert.system.dept.DeptConvert;
import cn.iocoder.yudao.service.model.system.dept.SystemDept;
import cn.iocoder.yudao.service.service.system.dept.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 部门 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class DeptApiImpl implements DeptApi {

    @Resource
    private DeptService deptService;

    @Override
    public DeptRespDTO getDept(Long id) {
        SystemDept dept = deptService.getDept(id);
        return DeptConvert.INSTANCE.convert03(dept);
    }

    @Override
    public List<DeptRespDTO> getDeptList(Collection<Long> ids) {
        List<SystemDept> depts = deptService.getDeptList(ids);
        return DeptConvert.INSTANCE.convertList03(depts);
    }

    @Override
    public void validateDeptList(Collection<Long> ids) {
        deptService.validateDeptList(ids);
    }

}
