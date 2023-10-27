package cn.iocoder.yudao.service.service.infra.job;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.quartz.core.service.JobLogFrameworkService;
import cn.iocoder.yudao.service.vo.infra.job.log.JobLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.job.log.JobLogPageReqVO;
import cn.iocoder.yudao.service.model.infra.job.InfraJobLog;

import java.util.Collection;
import java.util.List;

/**
 * Job 日志 Service 接口
 *
 * @author 芋道源码
 */
public interface JobLogService extends JobLogFrameworkService {

    /**
     * 获得定时任务
     *
     * @param id 编号
     * @return 定时任务
     */
    InfraJobLog getJobLog(Long id);

    /**
     * 获得定时任务列表
     *
     * @param ids 编号
     * @return 定时任务列表
     */
    List<InfraJobLog> getJobLogList(Collection<Long> ids);

    /**
     * 获得定时任务分页
     *
     * @param pageReqVO 分页查询
     * @return 定时任务分页
     */
    PageResult<InfraJobLog> getJobLogPage(JobLogPageReqVO pageReqVO);

    /**
     * 获得定时任务列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 定时任务分页
     */
    List<InfraJobLog> getJobLogList(JobLogExportReqVO exportReqVO);

}
