package cn.iocoder.yudao.service.service.infra.logger;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.api.infra.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.yudao.service.vo.infra.logger.loginlog.LoginLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.loginlog.LoginLogPageReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.loginlog.LoginLogRespVO;
import cn.iocoder.yudao.service.model.infra.logger.SystemLoginLog;

import javax.validation.Valid;
import java.util.List;

/**
 * 登录日志 Service 接口
 */
public interface LoginLogService {

    /**
     * 获得登录日志分页
     *
     * @param reqVO 分页条件
     * @return 登录日志分页
     */
    PageResult<LoginLogRespVO> getLoginLogPage(LoginLogPageReqVO reqVO);

    /**
     * 获得登录日志列表
     *
     * @param reqVO 列表条件
     * @return 登录日志列表
     */
    List<SystemLoginLog> getLoginLogList(LoginLogExportReqVO reqVO);

    /**
     * 创建登录日志
     *
     * @param reqDTO 日志信息
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);

}
