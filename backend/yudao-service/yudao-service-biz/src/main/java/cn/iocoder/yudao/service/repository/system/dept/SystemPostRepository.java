package cn.iocoder.yudao.service.repository.system.dept;

import cn.iocoder.yudao.service.vo.system.dept.post.PostExportReqVO;
import cn.iocoder.yudao.service.vo.system.dept.post.PostPageReqVO;
import cn.iocoder.yudao.service.model.system.dept.SystemPost;
import cn.iocoder.yudao.service.model.system.dept.SystemPostTable;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SystemPostRepository extends JRepository<SystemPost, Long> {
    SystemPostTable systemPostTable = SystemPostTable.$;
    default Page<SystemPost> selectPage(PostPageReqVO reqVO){
        return pager(reqVO.getPageNo() - 1, reqVO.getPageSize()).execute(
                sql()
                        .createQuery(systemPostTable)
                        .whereIf(StringUtils.hasText(reqVO.getCode()),systemPostTable.code().like(reqVO.getCode()))
                        .whereIf(StringUtils.hasText(reqVO.getName()),systemPostTable.name().like(reqVO.getName()))
                        .whereIf(reqVO.getStatus()!= null, systemPostTable.status().eq(reqVO.getStatus()))
                        .select(systemPostTable)
        );
    }

    default List<SystemPost> selectList(PostExportReqVO reqVO){
        return sql()
                .createQuery(systemPostTable)
                .whereIf(StringUtils.hasText(reqVO.getCode()),systemPostTable.code().like(reqVO.getCode()))
                .whereIf(StringUtils.hasText(reqVO.getName()),systemPostTable.name().like(reqVO.getName()))
                .whereIf(reqVO.getStatus()!= null, systemPostTable.status().eq(reqVO.getStatus()))
                .select(systemPostTable).execute();
    }

    Optional<SystemPost> findByName(String name);

    Optional<SystemPost> findByCode(String code);

    List<SystemPost> findByIdInAndStatusIn(Collection<Long> ids, Collection<Integer> statuses);
}
