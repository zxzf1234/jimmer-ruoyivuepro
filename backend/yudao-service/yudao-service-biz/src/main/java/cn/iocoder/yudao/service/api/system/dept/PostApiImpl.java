package cn.iocoder.yudao.service.api.system.dept;

import cn.iocoder.yudao.service.service.system.dept.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 岗位 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class PostApiImpl implements PostApi {

    @Resource
    private PostService postService;

    @Override
    public void validPostList(Collection<Long> ids) {
        postService.validatePostList(ids);
    }

}
