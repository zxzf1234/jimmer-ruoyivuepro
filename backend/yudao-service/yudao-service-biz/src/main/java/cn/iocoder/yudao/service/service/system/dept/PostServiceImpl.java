package cn.iocoder.yudao.service.service.system.dept;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.system.dept.post.*;
import cn.iocoder.yudao.service.convert.system.dept.PostConvert;
import cn.iocoder.yudao.service.model.system.dept.SystemPost;
import cn.iocoder.yudao.service.repository.system.dept.SystemPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.*;

/**
 * 岗位 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PostServiceImpl implements PostService {

    @Resource
    private SystemPostRepository systemPostRepository;

    @Override
    public Long createPost(PostCreateReq reqVO) {
        // 校验正确性
        validatePostForCreateOrUpdate(null, reqVO.getName(), reqVO.getCode());

        // 插入岗位
        SystemPost post = PostConvert.INSTANCE.convert(reqVO);
        post = systemPostRepository.insert(post);
        return post.id();
    }

    @Override
    public void updatePost(PostUpdateReq reqVO) {
        // 校验正确性
        validatePostForCreateOrUpdate(reqVO.getId(), reqVO.getName(), reqVO.getCode());

        // 更新岗位
        SystemPost updateObj = PostConvert.INSTANCE.convert(reqVO);
        systemPostRepository.update(updateObj);
    }

    @Override
    public void deletePost(Long id) {
        // 校验是否存在
        validatePostExists(id);
        // 删除部门
        systemPostRepository.deleteById(id);
    }

    private void validatePostForCreateOrUpdate(Long id, String name, String code) {
        // 校验自己存在
        validatePostExists(id);
        // 校验岗位名的唯一性
        validatePostNameUnique(id, name);
        // 校验岗位编码的唯一性
        validatePostCodeUnique(id, code);
    }

    private void validatePostNameUnique(Long id, String name) {
        Optional<SystemPost> opPost = systemPostRepository.findByName(name);
        if (!opPost.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的岗位
        if (id == null) {
            throw exception(POST_NAME_DUPLICATE);
        }
        if (opPost.get().id() != id) {
            throw exception(POST_NAME_DUPLICATE);
        }
    }

    private void validatePostCodeUnique(Long id, String code) {
        Optional<SystemPost> opPost = systemPostRepository.findByCode(code);
        if (!opPost.isPresent()) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的岗位
        if (id == null) {
            throw exception(POST_CODE_DUPLICATE);
        }
        if (opPost.get().id() != id) {
            throw exception(POST_CODE_DUPLICATE);
        }
    }

    private void validatePostExists(Long id) {
        if (id == null) {
            return;
        }
        if (systemPostRepository.findById(id) == null) {
            throw exception(POST_NOT_FOUND);
        }
    }

    @Override
    public List<SystemPost> getPostList(Collection<Long> ids, Collection<Integer> statuses) {
        return systemPostRepository.findByIdInAndStatusIn(ids, statuses);
    }

    @Override
    public PageResult<PostResp> getPostPage(PostPageReqVO reqVO) {
        Page<SystemPost> postPage = systemPostRepository.selectPage(reqVO);
        List<PostResp> postList =  PostConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(postList, postPage.getTotalElements());
    }

    @Override
    public List<SystemPost> getPostList(PostExportReqVO reqVO) {

        return systemPostRepository.selectList(reqVO);
    }

    @Override
    public SystemPost getPost(Long id) {
        return systemPostRepository.findById(id).get();
    }

    @Override
    public void validatePostList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得岗位信息
        List<SystemPost> posts = systemPostRepository.findByIds(ids);
        Map<Long, SystemPost> postMap = convertMap(posts, SystemPost::id);
        // 校验
        ids.forEach(id -> {
            SystemPost post = postMap.get(id);
            if (post == null) {
                throw exception(POST_NOT_FOUND);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(post.status())) {
                throw exception(POST_NOT_ENABLE, post.name());
            }
        });
    }
}
