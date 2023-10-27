package cn.iocoder.yudao.service.service.infra.logger;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.api.infra.logger.dto.ApiErrorLogCreateReqDTO;
import cn.iocoder.yudao.service.vo.infra.logger.apierrorlog.ApiErrorLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.apierrorlog.ApiErrorLogPageReqVO;
import cn.iocoder.yudao.service.model.infra.data.InfraApiErrorLog;

import java.util.List;

/**
 * API 错误日志 Service 接口
 *
 * @author 芋道源码
 */
public interface ApiErrorLogService {

    /**
     * 创建 API 错误日志
     *
     * @param createReqDTO API 错误日志
     */
    void createApiErrorLog(ApiErrorLogCreateReqDTO createReqDTO);

    /**
     * 获得 API 错误日志分页
     *
     * @param pageReqVO 分页查询
     * @return API 错误日志分页
     */
    PageResult<InfraApiErrorLog> getApiErrorLogPage(ApiErrorLogPageReqVO pageReqVO);

    /**
     * 获得 API 错误日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return API 错误日志分页
     */
    List<InfraApiErrorLog> getApiErrorLogList(ApiErrorLogExportReqVO exportReqVO);

    /**
     * 更新 API 错误日志已处理
     *
     * @param id API 日志编号
     * @param processStatus 处理结果
     * @param processUserId 处理人
     */
    void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId);

}
