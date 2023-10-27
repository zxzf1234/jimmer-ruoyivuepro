package cn.iocoder.yudao.service.api.infra.logger;

import cn.iocoder.yudao.service.api.infra.logger.dto.ApiAccessLogCreateReqDTO;

import javax.validation.Valid;

/**
 * API 访问日志的 API 接口
 *
 * @author 芋道源码
 */
public interface ApiAccessLogApi {

    /**
     * 创建 API 访问日志
     *
     * @param createDTO 创建信息
     */
    void createApiAccessLog(@Valid ApiAccessLogCreateReqDTO createDTO);

}
