package cn.iocoder.yudao.service.api.infra.logger;

import cn.iocoder.yudao.service.api.infra.logger.dto.ApiAccessLogCreateReqDTO;
import cn.iocoder.yudao.service.service.infra.logger.ApiAccessLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * API 访问日志的 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ApiAccessLogApiImpl implements ApiAccessLogApi {

    @Resource
    private ApiAccessLogService apiAccessLogService;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        apiAccessLogService.createApiAccessLog(createDTO);
    }

}
