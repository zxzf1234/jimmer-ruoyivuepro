package cn.iocoder.yudao.service.api.infra.logger;

import cn.iocoder.yudao.service.api.infra.logger.dto.ApiErrorLogCreateReqDTO;
import cn.iocoder.yudao.service.service.infra.logger.ApiErrorLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * API 访问日志的 API 接口
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ApiErrorLogApiImpl implements ApiErrorLogApi {

    @Resource
    private ApiErrorLogService apiErrorLogService;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        apiErrorLogService.createApiErrorLog(createDTO);
    }

}
