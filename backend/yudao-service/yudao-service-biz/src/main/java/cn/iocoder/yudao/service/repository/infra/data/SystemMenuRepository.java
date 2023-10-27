package cn.iocoder.yudao.service.repository.infra.data;

import cn.iocoder.yudao.service.vo.infra.permission.menu.MenuListReqVO;
import cn.iocoder.yudao.service.model.infra.data.SystemMenu;
import cn.iocoder.yudao.service.model.infra.data.SystemMenuTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SystemMenuRepository extends JRepository<SystemMenu, Long> {
    SystemMenuTable systemMenuTable = SystemMenuTable.$;
    default List<SystemMenu> selectList(MenuListReqVO reqVO){
        return sql()
                .createQuery(systemMenuTable)
                .whereIf(reqVO.getStatus() != null, systemMenuTable.status().eq(reqVO.getStatus()))
                .whereIf(StringUtils.hasText(reqVO.getName()), systemMenuTable.name().eq(reqVO.getName()))
                .select(systemMenuTable)
                .execute();
    };

    Optional<SystemMenu> findByParentIdAndName(String parentId, String name);

    long countByParentId(String menuId);

    Optional<SystemMenu> findById(UUID id);

    void deleteById(UUID id);

    List<SystemMenu> findByPermission(String permission);

    List<SystemMenu> findByTypeInAndStatusIn(Collection<Integer> types, Collection<Integer> statuses);

    List<SystemMenu> findByIdInAndTypeInAndStatusIn(Collection<UUID> menuIds, Collection<Integer> types, Collection<Integer> statuses);
}
