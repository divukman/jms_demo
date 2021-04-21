package tech.dimitar.jms.demo.jmsdemo.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.dimitar.jms.demo.jmsdemo.config.JmsConfig;
import tech.dimitar.jms.demo.jmsdemo.model.HelloWorldMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloSender {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

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

    @Scheduled(fixedRate = 2000)
    public void sendReceiveMessage() throws JMSException {
        log.info("SND RCV");

        final HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

       final Message replyMsg =  jmsTemplate.sendAndReceive(JmsConfig.SND_RCV_QUEUE, session -> {
            Message helloMsg = null;
            try {
                helloMsg = session.createTextMessage(objectMapper.writeValueAsString(message));
                helloMsg.setStringProperty("_type","tech.dimitar.jms.demo.jmsdemo.model.HelloWorldMessage");
                return helloMsg;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new JMSException(e.getMessage());
            }
        });

       log.info("Got reply: " + replyMsg.getBody(String.class));
    }
}
