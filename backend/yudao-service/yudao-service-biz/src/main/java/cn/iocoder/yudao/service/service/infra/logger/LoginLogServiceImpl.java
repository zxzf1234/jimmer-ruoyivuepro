package cn.iocoder.yudao.service.service.infra.logger;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.api.infra.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.yudao.service.vo.infra.logger.loginlog.LoginLogExportReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.loginlog.LoginLogPageReqVO;
import cn.iocoder.yudao.service.vo.infra.logger.loginlog.LoginLogRespVO;
import cn.iocoder.yudao.service.convert.infra.logger.LoginLogConvert;
import cn.iocoder.yudao.service.model.infra.logger.SystemLoginLog;
import cn.iocoder.yudao.service.repository.infra.logger.SystemLoginLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录日志 Service 实现
 */
@Service
@Validated
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private SystemLoginLogRepository systemLoginLogRepository;

    @Override
    public PageResult<LoginLogRespVO> getLoginLogPage(LoginLogPageReqVO reqVO) {
        Page<SystemLoginLog> postPage = systemLoginLogRepository.selectPage(reqVO);
        List<LoginLogRespVO> postList = LoginLogConvert.INSTANCE.convertPage(postPage);
        return new PageResult<>(postList, postPage.getTotalElements());
    }

    @Override
    public List<SystemLoginLog> getLoginLogList(LoginLogExportReqVO reqVO) {
        return systemLoginLogRepository.selectList(reqVO);
    }

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        SystemLoginLog loginLog = LoginLogConvert.INSTANCE.convert(reqDTO);
        systemLoginLogRepository.insert(loginLog);
    }

}
