package cn.iocoder.yudao.service.service.system.dept;

import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListAllSimpleOutput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListOutput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListInput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptUpdateInput;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptCreateInput;
import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptCreateReq;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptListReq;
import cn.iocoder.yudao.service.vo.system.dept.dept.DeptUpdateReq;
import cn.iocoder.yudao.service.model.system.dept.SystemDept;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 部门 Service 接口
 *
 * @author 芋道源码
 */
public interface DeptService {


    /**
     * 删除部门
     *
     * @param id 部门编号
     */
    void deleteDept(Long id);

    /**
     * 筛选部门列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return 部门列表
     */
    List<SystemDept> getDeptList(DeptListReq reqVO);

    /**
     * 获得所有子部门，从缓存中
     *
     * @param parentId 部门编号
     * @param recursive 是否递归获取所有
     * @return 子部门列表
     */
    List<SystemDept> getDeptListByParentId(Long parentId, boolean recursive);

    /**
     * 获得部门信息数组
     *
     * @param ids 部门编号数组
     * @return 部门信息数组
     */
    List<SystemDept> getDeptList(Collection<Long> ids);

    /**
     * 获得指定编号的部门 Map
     *
     * @param ids 部门编号数组
     * @return 部门 Map
     */
    default Map<Long, SystemDept> getDeptMap(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<SystemDept> list = getDeptList(ids);
        return CollectionUtils.convertMap(list, SystemDept::id);
    }

    /**
     * 获得部门信息
     *
     * @param id 部门编号
     * @return 部门信息
     */
    SystemDept getDept(Long id);

    /**
     * 校验部门们是否有效。如下情况，视为无效：
     * 1. 部门编号不存在
     * 2. 部门被禁用
     *
     * @param ids 角色编号数组
     */
    void validateDeptList(Collection<Long> ids);

    Long create(DeptCreateInput inputVO);

    Boolean update(DeptUpdateInput inputVO);

    Boolean delete(Long id);

    List<DeptListOutput> list(DeptListInput inputVO);

    List<DeptListAllSimpleOutput> listAllSimple();

}
