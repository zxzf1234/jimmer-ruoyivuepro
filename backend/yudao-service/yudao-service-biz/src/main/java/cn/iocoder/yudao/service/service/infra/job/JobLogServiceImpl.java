package cn.iocoder.yudao.service.service.infra.job;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.job.log.JobLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.job.log.JobLogPageReqVO;

import cn.iocoder.yudao.service.enums.job.JobLogStatusEnum;
import cn.iocoder.yudao.service.model.infra.job.InfraJobLog;
import cn.iocoder.yudao.service.model.infra.job.InfraJobLogDraft;
import cn.iocoder.yudao.service.repository.infra.job.InfraJobLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Job 日志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class JobLogServiceImpl implements JobLogService {

    @Resource
    private InfraJobLogRepository infraJobLogRepository;

    @Override
    public Long createJobLog(String jobId, LocalDateTime beginTime, String jobHandlerName, String jobHandlerParam, Integer executeIndex) {
        InfraJobLog log = InfraJobLogDraft.$.produce(draft -> {
            draft.setJobId(jobId)
                    .setHandlerName(jobHandlerName)
                    .setHandlerParam(jobHandlerParam)
                    .setExecuteIndex(executeIndex)
                    .setBeginTime(beginTime)
                    .setStatus(JobLogStatusEnum.RUNNING.getStatus());
        });
        log = infraJobLogRepository.insert(log);
        return log.id();
    }

    @Override
    @Async
    public void updateJobLogResultAsync(Long logId, LocalDateTime endTime, Integer duration, boolean success, String result) {
        try {
            InfraJobLog updateObj = InfraJobLogDraft.$.produce(draft -> {
                draft.setId(logId)
                        .setEndTime(endTime)
                        .setDuration(duration)
                        .setStatus(success ? JobLogStatusEnum.SUCCESS.getStatus() : JobLogStatusEnum.FAILURE.getStatus())
                        .setResult(result);
            });
            infraJobLogRepository.update(updateObj);
        } catch (Exception ex) {
            log.error("[updateJobLogResultAsync][logId({}) endTime({}) duration({}) success({}) result({})]",
                    logId, endTime, duration, success, result);
        }
    }

    @Override
    public InfraJobLog getJobLog(Long id) {
        return infraJobLogRepository.findById(id).get();
    }

    @Override
    public List<InfraJobLog> getJobLogList(Collection<Long> ids) {
        return infraJobLogRepository.findByIds(ids);
    }

    @Override
    public PageResult<InfraJobLog> getJobLogPage(JobLogPageReqVO pageReqVO) {
        Page<InfraJobLog> postPage = infraJobLogRepository.selectPage(pageReqVO);
        return new PageResult<>(postPage.toList(), postPage.getTotalElements());
    }

    @Override
    public List<InfraJobLog> getJobLogList(JobLogExportReqVO exportReqVO) {
        return infraJobLogRepository.selectList(exportReqVO);
    }

}
