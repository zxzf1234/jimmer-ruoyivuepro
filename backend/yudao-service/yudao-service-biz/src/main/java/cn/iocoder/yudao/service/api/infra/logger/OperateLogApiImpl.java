package cn.iocoder.yudao.service.api.infra.logger;

import cn.iocoder.yudao.service.api.infra.logger.dto.OperateLogCreateReqDTO;
import cn.iocoder.yudao.service.service.infra.logger.OperateLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 操作日志 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogService operateLogService;

    @Override
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
    }

}
