package cn.iocoder.yudao.service.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.service.model.system.user.SystemUser;
import cn.iocoder.yudao.service.repository.system.user.SystemUserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DemoJob implements JobHandler {

    @Resource
    private SystemUserRepository systemUserRepository;

    @Override
    public String execute(String param) throws Exception {
        List<SystemUser> users = systemUserRepository.findAll();
        return "用户数量：" + users.size();
    }

}
