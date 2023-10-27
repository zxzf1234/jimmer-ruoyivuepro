package cn.iocoder.yudao.service.repository.system.dept;

import cn.iocoder.yudao.service.model.system.dept.SystemUserPost;
import cn.iocoder.yudao.service.model.system.dept.SystemUserPostTable;
import org.babyfish.jimmer.spring.repository.JRepository;

import java.util.Collection;
import java.util.List;

public interface SystemUserPostRepository extends JRepository<SystemUserPost, Long> {
    SystemUserPostTable systemUserPostTable = SystemUserPostTable.$;
    int deleteByUserId(Long userId);

    List<SystemUserPost> findByUserId(Long userId);

    void deleteByUserIdAndPostIdIn(Long userId, Collection<Long> deletePostIds);

}
