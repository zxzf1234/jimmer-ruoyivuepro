package cn.iocoder.yudao.service.service.system.notice;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.system.notify.notice.*;
import cn.iocoder.yudao.service.vo.system.notify.notice.NoticeCreateReq;
import cn.iocoder.yudao.service.vo.system.notify.notice.NoticeResp;
import cn.iocoder.yudao.service.convert.system.notice.NoticeConvert;
import cn.iocoder.yudao.service.model.system.notify.SystemNotice;
import cn.iocoder.yudao.service.repository.system.notify.SystemNoticeRepository;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.NOTICE_NOT_FOUND;

/**
 * 通知公告 Service 实现类
 *
 * @author 芋道源码
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private SystemNoticeRepository systemNoticeRepository;

    @Override
    public Long createNotice(NoticeCreateReq reqVO) {
        SystemNotice notice = NoticeConvert.INSTANCE.convert(reqVO);
        notice = systemNoticeRepository.insert(notice);
        return notice.id();
    }

    @Override
    public void updateNotice(NoticeUpdateReq reqVO) {
        // 校验是否存在
        validateNoticeExists(reqVO.getId());
        // 更新通知公告
        SystemNotice updateObj = NoticeConvert.INSTANCE.convert(reqVO);
        systemNoticeRepository.update(updateObj);
    }

    @Override
    public void deleteNotice(Long id) {
        // 校验是否存在
        validateNoticeExists(id);
        // 删除通知公告
        systemNoticeRepository.deleteById(id);
    }

    @Override
    public PageResult<NoticeResp> getNoticePage(NoticePageReqVO reqVO) {
        Page<SystemNotice> postPage = systemNoticeRepository.selectPage(reqVO);
        List<NoticeResp> listPage = NoticeConvert.INSTANCE.NoticeRespVO(postPage);
        return new PageResult<>(listPage, postPage.getTotalElements());
    }

    @Override
    public SystemNotice getNotice(Long id) {
        return systemNoticeRepository.findById(id).get();
    }

    @VisibleForTesting
    public void validateNoticeExists(Long id) {
        if (id == null) {
            return;
        }
        Optional<SystemNotice> opNotice = systemNoticeRepository.findById(id);
        if (!opNotice.isPresent()) {
            throw exception(NOTICE_NOT_FOUND);
        }
    }

}
