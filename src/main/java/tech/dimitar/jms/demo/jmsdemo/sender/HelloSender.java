package tech.dimitar.jms.demo.jmsdemo.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.dimitar.jms.demo.jmsdemo.config.JmsConfig;
import tech.dimitar.jms.demo.jmsdemo.model.HelloWorldMessage;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloSender {
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        log.info("Sending hello world message to jms queue...");

        final HelloWorldMessage helloWorldMessage = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE_NAME, helloWorldMessage);
    }
}
