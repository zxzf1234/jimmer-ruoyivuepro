package cn.iocoder.yudao.service.mq.consumer.mail;

import cn.iocoder.yudao.framework.mq.core.stream.AbstractStreamMessageListener;
import cn.iocoder.yudao.service.mq.message.mail.MailSendMessage;
import cn.iocoder.yudao.service.service.infra.mail.MailSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link MailSendMessage} 的消费者
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class MailSendConsumer extends AbstractStreamMessageListener<MailSendMessage> {

    @Resource
    private MailSendService mailSendService;

    @Override
    public void onMessage(MailSendMessage message) {
        log.info("[onMessage][消息内容({})]", message);
        mailSendService.doSendMail(message);
    }

}
