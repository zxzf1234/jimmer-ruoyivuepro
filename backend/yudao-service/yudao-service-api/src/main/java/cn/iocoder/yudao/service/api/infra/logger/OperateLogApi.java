package cn.iocoder.yudao.service.api.infra.logger;

import cn.iocoder.yudao.service.api.infra.logger.dto.OperateLogCreateReqDTO;

import javax.validation.Valid;

/**
 * 操作日志 API 接口
 *
 * @author 芋道源码
 */
public interface OperateLogApi {

    /**
     * 创建操作日志
     *
     * @param createReqDTO 请求
     */
    void createOperateLog(@Valid OperateLogCreateReqDTO createReqDTO);

}
