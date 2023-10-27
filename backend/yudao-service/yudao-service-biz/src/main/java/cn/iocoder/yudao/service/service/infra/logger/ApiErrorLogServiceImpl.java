package cn.iocoder.yudao.service.service.infra.logger;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.api.infra.logger.dto.ApiErrorLogCreateReqDTO;
import cn.iocoder.yudao.service.vo.infra.logger.apierrorlog.ApiErrorLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.apierrorlog.ApiErrorLogPageReqVO;
import cn.iocoder.yudao.service.convert.infra.logger.ApiErrorLogConvert;
import cn.iocoder.yudao.service.enums.logger.ApiErrorLogProcessStatusEnum;
import cn.iocoder.yudao.service.model.infra.data.InfraApiErrorLog;
import cn.iocoder.yudao.service.model.infra.data.InfraApiErrorLogDraft;
import cn.iocoder.yudao.service.repository.infra.data.InfraApiErrorLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.API_ERROR_LOG_NOT_FOUND;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.API_ERROR_LOG_PROCESSED;

/**
 * API 错误日志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ApiErrorLogServiceImpl implements ApiErrorLogService {

    @Resource
    private InfraApiErrorLogRepository infraApiErrorLogRepository;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        InfraApiErrorLog apiErrorLog = ApiErrorLogConvert.INSTANCE.convert(createDTO);
        apiErrorLog = InfraApiErrorLogDraft.$.produce(apiErrorLog, draft -> {
            draft.setProcessStatus(ApiErrorLogProcessStatusEnum.INIT.getStatus());
        });
        infraApiErrorLogRepository.insert(apiErrorLog);
    }

    @Override
    public PageResult<InfraApiErrorLog> getApiErrorLogPage(ApiErrorLogPageReqVO pageReqVO) {
        Page<InfraApiErrorLog> postPage = infraApiErrorLogRepository.selectPage(pageReqVO);
        return new PageResult<>(postPage.toList(), postPage.getTotalElements());
    }

    @Override
    public List<InfraApiErrorLog> getApiErrorLogList(ApiErrorLogExportReqVO exportReqVO) {
        return infraApiErrorLogRepository.selectList(exportReqVO);
    }

    @Override
    public void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId) {
        Optional<InfraApiErrorLog> opErrorLog = infraApiErrorLogRepository.findById(id);
        if (!opErrorLog.isPresent()) {
            throw exception(API_ERROR_LOG_NOT_FOUND);
        }
        if (!ApiErrorLogProcessStatusEnum.INIT.getStatus().equals(opErrorLog.get().processStatus())) {
            throw exception(API_ERROR_LOG_PROCESSED);
        }
        // 标记处理
        InfraApiErrorLog errorLog = InfraApiErrorLogDraft.$.produce(draft -> {
            draft.setId(id).setProcessStatus(processStatus).setProcessUserId(processUserId)
                    .setProcessTime(LocalDateTime.now());
        });
        infraApiErrorLogRepository.update(errorLog);
    }

}
