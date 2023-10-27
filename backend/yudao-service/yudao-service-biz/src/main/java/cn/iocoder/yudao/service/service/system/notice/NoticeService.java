package cn.iocoder.yudao.service.service.system.notice;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.system.notify.notice.NoticeCreateReq;
import cn.iocoder.yudao.service.vo.system.notify.notice.NoticePageReqVO;
import cn.iocoder.yudao.service.vo.system.notify.notice.NoticeResp;
import cn.iocoder.yudao.service.vo.system.notify.notice.NoticeUpdateReq;
import cn.iocoder.yudao.service.model.system.notify.SystemNotice;

/**
 * 通知公告 Service 接口
 */
public interface NoticeService {

    /**
     * 创建岗位公告公告
     *
     * @param reqVO 岗位公告公告信息
     * @return 岗位公告公告编号
     */
    Long createNotice(NoticeCreateReq reqVO);

    /**
     * 更新岗位公告公告
     *
     * @param reqVO 岗位公告公告信息
     */
    void updateNotice(NoticeUpdateReq reqVO);

    /**
     * 删除岗位公告公告信息
     *
     * @param id 岗位公告公告编号
     */
    void deleteNotice(Long id);

    /**
     * 获得岗位公告公告分页列表
     *
     * @param reqVO 分页条件
     * @return 部门分页列表
     */
    PageResult<NoticeResp> getNoticePage(NoticePageReqVO reqVO);

    /**
     * 获得岗位公告公告信息
     *
     * @param id 岗位公告公告编号
     * @return 岗位公告公告信息
     */
    SystemNotice getNotice(Long id);

}
