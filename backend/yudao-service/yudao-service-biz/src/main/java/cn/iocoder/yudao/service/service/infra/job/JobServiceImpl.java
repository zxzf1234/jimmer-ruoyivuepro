package cn.iocoder.yudao.service.service.infra.job;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.quartz.core.scheduler.SchedulerManager;
import cn.iocoder.yudao.framework.quartz.core.util.CronUtils;
import cn.iocoder.yudao.service.model.infra.job.InfraJobProps;
import cn.iocoder.yudao.service.vo.infra.job.job.JobCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.job.job.JobExportReqVO;
import cn.iocoder.yudao.service.vo.infra.job.job.JobPageReqVO;
import cn.iocoder.yudao.service.vo.infra.job.job.JobUpdateReqVO;
import cn.iocoder.yudao.service.enums.job.JobStatusEnum;
import cn.iocoder.yudao.service.model.infra.job.InfraJob;
import cn.iocoder.yudao.service.convert.infra.job.JobConvert;
import cn.iocoder.yudao.service.model.infra.job.InfraJobDraft;
import cn.iocoder.yudao.service.repository.infra.job.InfraJobRepository;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.babyfish.jimmer.ImmutableObjects;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.infra.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.containsAny;

/**
 * 定时任务 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class JobServiceImpl implements JobService {

    @Resource
    private InfraJobRepository infraJobRepository;

    @Resource
    private SchedulerManager schedulerManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createJob(JobCreateReqVO createReqVO) throws SchedulerException {
        validateCronExpression(createReqVO.getCronExpression());
        // 校验唯一性
        if (infraJobRepository.findByHandlerName(createReqVO.getHandlerName()).isPresent()) {
            throw exception(JOB_HANDLER_EXISTS);
        }
        // 插入
        InfraJob job = JobConvert.INSTANCE.convert(createReqVO);
        job = InfraJobDraft.$.produce(job, draft -> {
            draft.setStatus(JobStatusEnum.INIT.getStatus()).setId(UUID.randomUUID());
        });
        fillJobMonitorTimeoutEmpty(job);
        job = infraJobRepository.insert(job);

        // 添加 Job 到 Quartz 中
        schedulerManager.addJob(job.id(), job.handlerName(), job.handlerParam(), job.cronExpression(),
                createReqVO.getRetryCount(), createReqVO.getRetryInterval());
        // 更新
        InfraJob finalJob = job;
        InfraJob updateObj = InfraJobDraft.$.produce(draft -> {
            draft.setId(finalJob.id()).setStatus(JobStatusEnum.NORMAL.getStatus());
        });
        infraJobRepository.update(updateObj);

        // 返回
        return job.id().toString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(JobUpdateReqVO updateReqVO) throws SchedulerException {
        validateCronExpression(updateReqVO.getCronExpression());
        // 校验存在
        InfraJob job = validateJobExists(updateReqVO.getId());
        // 只有开启状态，才可以修改.原因是，如果出暂停状态，修改 Quartz Job 时，会导致任务又开始执行
        if (job.status()!=JobStatusEnum.NORMAL.getStatus()) {
            throw exception(JOB_UPDATE_ONLY_NORMAL_STATUS);
        }
        // 更新
        InfraJob updateObj = JobConvert.INSTANCE.convert(updateReqVO);
        updateObj = fillJobMonitorTimeoutEmpty(updateObj);
        infraJobRepository.update(updateObj);

        // 更新 Job 到 Quartz 中
        schedulerManager.updateJob(job.handlerName(), updateReqVO.getHandlerParam(), updateReqVO.getCronExpression(),
                updateReqVO.getRetryCount(), updateReqVO.getRetryInterval());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJobStatus(UUID id, Integer status) throws SchedulerException {
        // 校验 status
        if (!containsAny(status, JobStatusEnum.NORMAL.getStatus(), JobStatusEnum.STOP.getStatus())) {
            throw exception(JOB_CHANGE_STATUS_INVALID);
        }
        // 校验存在
        InfraJob job = validateJobExists(id);
        // 校验是否已经为当前状态
        if (job.status() == status) {
            throw exception(JOB_CHANGE_STATUS_EQUALS);
        }
        // 更新 Job 状态
        InfraJob updateObj = InfraJobDraft.$.produce(draft -> {
            draft.setId(id).setStatus(status);
        });

        infraJobRepository.update(updateObj);

        // 更新状态 Job 到 Quartz 中
        if (JobStatusEnum.NORMAL.getStatus().equals(status)) { // 开启
            schedulerManager.resumeJob(job.handlerName());
        } else { // 暂停
            schedulerManager.pauseJob(job.handlerName());
        }
    }

    @Override
    public void triggerJob(UUID id) throws SchedulerException {
        // 校验存在
        InfraJob job = validateJobExists(id);

        // 触发 Quartz 中的 Job
        schedulerManager.triggerJob(job.id().toString(), job.handlerName(), job.handlerParam());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJob(UUID id) throws SchedulerException {
        // 校验存在
        InfraJob job = validateJobExists(id);
        // 更新
        infraJobRepository.deleteById(id);

        // 删除 Job 到 Quartz 中
        schedulerManager.deleteJob(job.handlerName());
    }

    private InfraJob validateJobExists(UUID id) {
        Optional<InfraJob> opJob = infraJobRepository.findById(id);
        if (!opJob.isPresent()) {
            throw exception(JOB_NOT_EXISTS);
        }
        return opJob.get();
    }

    private void validateCronExpression(String cronExpression) {
        if (!CronUtils.isValid(cronExpression)) {
            throw exception(JOB_CRON_EXPRESSION_VALID);
        }
    }

    @Override
    public InfraJob getJob(UUID id) {
        return infraJobRepository.findById(id).get();
    }

    @Override
    public List<InfraJob> getJobList(Collection<UUID> ids) {
        return infraJobRepository.findByIdIn(ids);
    }

    @Override
    public PageResult<InfraJob> getJobPage(JobPageReqVO pageReqVO) {
        Page<InfraJob> postPage = infraJobRepository.selectPage(pageReqVO);
		return new PageResult<>(postPage.toList(), postPage.getTotalElements());
    }

    @Override
    public List<InfraJob> getJobList(JobExportReqVO exportReqVO) {
		return infraJobRepository.selectList(exportReqVO);
    }

    private static InfraJob fillJobMonitorTimeoutEmpty(InfraJob job) {
        if (ImmutableObjects.isLoaded(job, InfraJobProps.MONITOR_TIMEOUT)) {
            job = InfraJobDraft.$.produce(job, draft -> {
                draft.setMonitorTimeout(0);
            });
        }
        return job;
    }

}
