package cn.iocoder.yudao.service.service.infra.job;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.job.job.JobCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.job.job.JobExportReqVO;
import cn.iocoder.yudao.service.vo.infra.job.job.JobPageReqVO;
import cn.iocoder.yudao.service.vo.infra.job.job.JobUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.job.InfraJob;
import org.quartz.SchedulerException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * 定时任务 Service 接口
 *
 * @author 芋道源码
 */
public interface JobService {

    /**
     * 创建定时任务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createJob(@Valid JobCreateReqVO createReqVO) throws SchedulerException;

    /**
     * 更新定时任务
     *
     * @param updateReqVO 更新信息
     */
    void updateJob(@Valid JobUpdateReqVO updateReqVO) throws SchedulerException;

    /**
     * 更新定时任务的状态
     *
     * @param id 任务编号
     * @param status 状态
     */
    void updateJobStatus(UUID id, Integer status) throws SchedulerException;

    /**
     * 触发定时任务
     *
     * @param id 任务编号
     */
    void triggerJob(UUID id) throws SchedulerException;

    /**
     * 删除定时任务
     *
     * @param id 编号
     */
    void deleteJob(UUID id) throws SchedulerException;

    /**
     * 获得定时任务
     *
     * @param id 编号
     * @return 定时任务
     */
    InfraJob getJob(UUID id);

    /**
     * 获得定时任务列表
     *
     * @param ids 编号
     * @return 定时任务列表
     */
    List<InfraJob> getJobList(Collection<UUID> ids);

    /**
     * 获得定时任务分页
     *
     * @param pageReqVO 分页查询
     * @return 定时任务分页
     */
    PageResult<InfraJob> getJobPage(JobPageReqVO pageReqVO);

    /**
     * 获得定时任务列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 定时任务分页
     */
    List<InfraJob> getJobList(JobExportReqVO exportReqVO);

}
