package cn.iocoder.yudao.service.service.infra.logger;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.api.infra.logger.dto.ApiAccessLogCreateReqDTO;
import cn.iocoder.yudao.service.vo.infra.logger.apiaccesslog.ApiAccessLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.apiaccesslog.ApiAccessLogPageReqVO;
import cn.iocoder.yudao.service.convert.infra.logger.ApiAccessLogConvert;
import cn.iocoder.yudao.service.model.infra.data.InfraApiAccessLog;
import cn.iocoder.yudao.service.repository.infra.data.InfraApiAccessLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * API 访问日志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ApiAccessLogServiceImpl implements ApiAccessLogService {

    @Resource
    private InfraApiAccessLogRepository infraApiAccessLogRepository;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        InfraApiAccessLog apiAccessLog = ApiAccessLogConvert.INSTANCE.convert(createDTO);
        infraApiAccessLogRepository.insert(apiAccessLog);
    }

    @Override
    public PageResult<InfraApiAccessLog> getApiAccessLogPage(ApiAccessLogPageReqVO pageReqVO) {
        Page<InfraApiAccessLog> postPage = infraApiAccessLogRepository.selectPage(pageReqVO);
        return new PageResult<>(postPage.toList(), postPage.getTotalElements());
    }

    @Override
    public List<InfraApiAccessLog> getApiAccessLogList(ApiAccessLogExportReqVO exportReqVO) {
        return infraApiAccessLogRepository.selectList(exportReqVO);
    }

}
