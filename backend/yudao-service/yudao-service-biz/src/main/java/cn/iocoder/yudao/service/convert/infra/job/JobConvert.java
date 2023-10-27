package cn.iocoder.yudao.service.convert.infra.job;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.job.job.JobCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.job.job.JobExcelVO;
import cn.iocoder.yudao.service.vo.infra.job.job.JobRespVO;
import cn.iocoder.yudao.service.vo.infra.job.job.JobUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.job.InfraJob;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 定时任务 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface JobConvert {

    JobConvert INSTANCE = Mappers.getMapper(JobConvert.class);

    InfraJob convert(JobCreateReqVO bean);

    InfraJob convert(JobUpdateReqVO bean);

    JobRespVO convert(InfraJob bean);

    List<JobRespVO> convertList(List<InfraJob> list);

    PageResult<JobRespVO> convertPage(PageResult<InfraJob> page);

    List<JobExcelVO> convertList02(List<InfraJob> list);

}
