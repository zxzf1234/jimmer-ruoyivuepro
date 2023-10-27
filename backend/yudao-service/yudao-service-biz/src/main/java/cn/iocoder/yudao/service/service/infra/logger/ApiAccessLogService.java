package cn.iocoder.yudao.service.service.infra.logger;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.api.infra.logger.dto.ApiAccessLogCreateReqDTO;
import cn.iocoder.yudao.service.vo.infra.logger.apiaccesslog.ApiAccessLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.apiaccesslog.ApiAccessLogPageReqVO;
import cn.iocoder.yudao.service.model.infra.data.InfraApiAccessLog;

import java.util.List;

/**
 * API 访问日志 Service 接口
 *
 * @author 芋道源码
 */
public interface ApiAccessLogService {

    /**
     * 创建 API 访问日志
     *
     * @param createReqDTO API 访问日志
     */
    void createApiAccessLog(ApiAccessLogCreateReqDTO createReqDTO);

    /**
     * 获得 API 访问日志分页
     *
     * @param pageReqVO 分页查询
     * @return API 访问日志分页
     */
    PageResult<InfraApiAccessLog> getApiAccessLogPage(ApiAccessLogPageReqVO pageReqVO);

    /**
     * 获得 API 访问日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return API 访问日志分页
     */
    List<InfraApiAccessLog> getApiAccessLogList(ApiAccessLogExportReqVO exportReqVO);

}
