package cn.iocoder.yudao.service.service.infra.data;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.XmlUtil;
import cn.iocoder.yudao.service.framework.codegen.config.SchemaHistory;
import cn.iocoder.yudao.service.model.infra.data.FlywaySchemaHistory;
import cn.iocoder.yudao.service.repository.infra.data.FlywaySchemaHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.xpath.XPathConstants;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Profile("local")
public class SchemaHistoryServiceImpl implements SchemaHistoryService{
    @Resource
    private SchemaHistory schemaHistory;

    @Resource
    FlywaySchemaHistoryRepository flywaySchemaHistoryRepository;

    @Override
    @EventListener(value = ApplicationReadyEvent.class)
    @Async
    @Scheduled(cron = "0 0 0 * * ?")
    public void initLocalCache() {
        String version = DateUtil.format(LocalDateTime.now(), "yyyyMMdd") ;
        String gitUserName = RuntimeUtil.execForStr("git config user.name");
        gitUserName = gitUserName.substring(0, gitUserName.length() - 1);
        String path = ResourceUtil.getResource("codegen").toString().replace("target/classes", "src/main/resources") + "\\code_user.xml";
        Document docResult= XmlUtil.readXML(path);
        Object obGitUserId = XmlUtil.getByXPath("//codeUser/" + gitUserName, docResult, XPathConstants.STRING);
        if (obGitUserId == null)
            return;
        int gitUserId = Convert.toInt(obGitUserId);
        schemaHistory.setCurGitUserId(gitUserId);
        Optional<FlywaySchemaHistory> opInfraSchemaHistory = flywaySchemaHistoryRepository.findFirstByVersionLikeOrderByInstalledRankDesc(version + "." + gitUserId + ".");
        if (!opInfraSchemaHistory.isPresent())
            schemaHistory.setCurGitUserVersion(0);
        else
            schemaHistory.setCurGitUserVersion(Convert.toInt(Objects.requireNonNull(opInfraSchemaHistory.get().version()).replace( version + "." + gitUserId + "." , "")));
    }
}
