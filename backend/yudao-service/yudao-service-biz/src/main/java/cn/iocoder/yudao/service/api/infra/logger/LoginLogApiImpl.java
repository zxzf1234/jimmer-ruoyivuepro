package cn.iocoder.yudao.service.api.infra.logger;

import cn.iocoder.yudao.service.api.infra.logger.dto.LoginLogCreateReqDTO;
import cn.iocoder.yudao.service.service.infra.logger.LoginLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 登录日志的 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class LoginLogApiImpl implements LoginLogApi {

    @Resource
    private LoginLogService loginLogService;

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        loginLogService.createLoginLog(reqDTO);
    }

}
