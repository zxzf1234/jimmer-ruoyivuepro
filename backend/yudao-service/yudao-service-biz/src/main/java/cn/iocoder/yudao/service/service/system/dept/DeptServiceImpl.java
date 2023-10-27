package cn.iocoder.yudao.service.service.system.dept;

import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListAllSimpleOutput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListOutput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListInput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptUpdateInput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptCreateInput;
import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListReq;
import cn.iocoder.yudao.service.convert.system.dept.DeptConvert;
import cn.iocoder.yudao.service.enums.system.dept.DeptIdEnum;
import cn.iocoder.yudao.service.model.system.dept.SystemDept;
import cn.iocoder.yudao.service.repository.system.dept.SystemDeptRepository;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.*;

/**
 * 部门 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class DeptServiceImpl implements DeptService {

    @Resource
    private SystemDeptRepository systemDeptRepository;

    @Override
    public void deleteDept(Long id) {
        // 校验是否存在
        validateDeptExists(id);
        // 校验是否有子部门
        if (systemDeptRepository.countByParentId(id) > 0) {
            throw exception(DEPT_EXITS_CHILDREN);
        }
        // 删除部门
        systemDeptRepository.deleteById(id);
    }

    @Override
    public List<SystemDept> getDeptList(DeptListReq reqVO) {
        return systemDeptRepository.selectList(reqVO);
    }

    @Override
    public List<SystemDept> getDeptListByParentId(Long parentId, boolean recursive) {
        if (parentId == null) {
            return Collections.emptyList();
        }
        List<SystemDept> depts = systemDeptRepository.findAll();
        Multimap<Long, SystemDept> parentDept;
        ImmutableMultimap.Builder<Long, SystemDept> parentBuilder = ImmutableMultimap.builder();
        depts.forEach(SystemDept -> {
            parentBuilder.put(SystemDept.parentId(), SystemDept);
        });
        parentDept = parentBuilder.build();
        List<SystemDept> result = new ArrayList<>();
        // 递归，简单粗暴
       getDeptsByParentId(result, parentId,
               recursive ? Integer.MAX_VALUE : 1, // 如果递归获取，则无限；否则，只递归 1 次
               parentDept);
        return result;
    }

    /**
     * 递归获取所有的子部门，添加到 result 结果
     *
     * @param result 结果
     * @param parentId 父编号
     * @param recursiveCount 递归次数
     * @param parentDeptMap 父部门 Map，使用缓存，避免变化
     */
    private void getDeptsByParentId(List<SystemDept> result, Long parentId, int recursiveCount,
                                             Multimap<Long, SystemDept> parentDeptMap) {
        // 递归次数为 0，结束！
        if (recursiveCount == 0) {
            return;
        }

        // 获得子部门
        Collection<SystemDept> depts = parentDeptMap.get(parentId);
        if (CollUtil.isEmpty(depts)) {
            return;
        }
        result.addAll(depts);

        // 继续递归
        depts.forEach(dept -> getDeptsByParentId(result, dept.id(),
                recursiveCount - 1, parentDeptMap));
    }

    private void validateForCreateOrUpdate(Long id, Long parentId, String name) {
        // 校验自己存在
        validateDeptExists(id);
        // 校验父部门的有效性
        validateParentDeptEnable(id, parentId);
        // 校验部门名的唯一性
        validateDeptNameUnique(id, parentId, name);
    }

    private void validateParentDeptEnable(Long id, Long parentId) {
        if (parentId == null || DeptIdEnum.ROOT.getId().equals(parentId)) {
            return;
        }
        // 不能设置自己为父部门
        if (parentId.equals(id)) {
            throw exception(DEPT_PARENT_ERROR);
        }
        // 父岗位不存在
        Optional<SystemDept> opDept = systemDeptRepository.findById(parentId);
        if (!opDept.isPresent()) {
            throw exception(DEPT_PARENT_NOT_EXITS);
        }
        // 父部门被禁用
        if (!CommonStatusEnum.ENABLE.getStatus().equals(opDept.get().status())) {
            throw exception(DEPT_NOT_ENABLE);
        }
        // 父部门不能是原来的子部门
        List<SystemDept> children = getDeptListByParentId(id, true);
        if (children.stream().anyMatch(dept1 -> dept1.id() == parentId)) {
            throw exception(DEPT_PARENT_IS_CHILD);
        }
    }

    private void validateDeptExists(Long id) {
        if (id == null) {
            return;
        }
        Optional<SystemDept> opDept = systemDeptRepository.findById(id);
        if (!opDept.isPresent()) {
            throw exception(DEPT_NOT_FOUND);
        }
    }

    private void validateDeptNameUnique(Long id, Long parentId, String name) {
        Optional<SystemDept> opMenu = systemDeptRepository.findByParentIdAndName(parentId, name);
        if (!opMenu.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的岗位
        if (id == null) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
        if (opMenu.get().id() != id) {
            throw exception(DEPT_NAME_DUPLICATE);
        }
    }

    @Override
    public List<SystemDept> getDeptList(Collection<Long> ids) {
        return systemDeptRepository.findByIds(ids);
    }

    @Override
    public SystemDept getDept(Long id) {
        return systemDeptRepository.findById(id).get();
    }

    @Override
    public void validateDeptList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得科室信息
        Map<Long, SystemDept> deptMap = getDeptMap(ids);
        // 校验
        ids.forEach(id -> {
            SystemDept dept = deptMap.get(id);
            if (dept == null) {
                throw exception(DEPT_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dept.status())) {
                throw exception(DEPT_NOT_ENABLE, dept.name());
            }
        });
    }

    @Override
    public Long create(DeptCreateInput inputVO) {
        // 校验正确性
        if (inputVO.getParentId() == null) {
            inputVO.setParentId(DeptIdEnum.ROOT.getId());
        }
        validateForCreateOrUpdate(null, inputVO.getParentId(), inputVO.getName());
        // 插入部门
        SystemDept dept = DeptConvert.INSTANCE.createInputConvert(inputVO);
        dept = systemDeptRepository.insert(dept);
        return dept.id();
    }

    @Override
    public Boolean update(DeptUpdateInput inputVO) {
        // 校验正确性
        if (inputVO.getParentId() == null) {
            inputVO.setParentId(DeptIdEnum.ROOT.getId());
        }
        validateForCreateOrUpdate(inputVO.getId(), inputVO.getParentId(), inputVO.getName());
        // 更新部门
        SystemDept updateObj = DeptConvert.INSTANCE.updateInputConvert(inputVO);
        systemDeptRepository.update(updateObj);
        return true;
    }

    @Override
    public Boolean delete(Long id) {
        // 校验是否存在
        validateDeptExists(id);
        // 校验是否有子部门
        if (systemDeptRepository.countByParentId(id) > 0) {
            throw exception(DEPT_EXITS_CHILDREN);
        }
        // 删除部门
        systemDeptRepository.deleteById(id);
        return true;
    }

    @Override
    public List<DeptListOutput> list(DeptListInput inputVO) {
        List<SystemDept> list = systemDeptRepository.selectList(inputVO);
        list.sort(Comparator.comparing(SystemDept::sort));
        return DeptConvert.INSTANCE.listListOutputConvert(list);
    }

    @Override
    public List<DeptListAllSimpleOutput> listAllSimple() {
        // 获得部门列表，只要开启状态的
        DeptListReq reqVO = new DeptListReq();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<SystemDept> list = systemDeptRepository.selectList(reqVO);
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SystemDept::sort));
        return DeptConvert.INSTANCE.listAllSimpleListOutputConvert(list);
    }

}
