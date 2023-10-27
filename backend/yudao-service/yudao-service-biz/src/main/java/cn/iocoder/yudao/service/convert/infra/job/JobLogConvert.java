package cn.iocoder.yudao.service.convert.infra.job;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.job.log.JobLogExcelVO;
import cn.iocoder.yudao.service.vo.infra.job.log.JobLogRespVO;
import cn.iocoder.yudao.service.model.infra.job.InfraJobLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 定时任务日志 Convert
 *
 * @author 芋艿
 */
@Mapper
public interface JobLogConvert {

    JobLogConvert INSTANCE = Mappers.getMapper(JobLogConvert.class);

    JobLogRespVO convert(InfraJobLog bean);

    List<JobLogRespVO> convertList(List<InfraJobLog> list);

    PageResult<JobLogRespVO> convertPage(PageResult<InfraJobLog> page);

    List<JobLogExcelVO> convertList02(List<InfraJobLog> list);

}
