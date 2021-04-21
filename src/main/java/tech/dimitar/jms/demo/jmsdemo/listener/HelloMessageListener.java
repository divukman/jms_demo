package tech.dimitar.jms.demo.jmsdemo.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tech.dimitar.jms.demo.jmsdemo.config.JmsConfig;
import tech.dimitar.jms.demo.jmsdemo.model.HelloWorldMessage;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE_NAME)
    public void listen(
            @Payload HelloWorldMessage helloWorldMessage,
            @Headers MessageHeaders headers,
            Message message
    ) {
        log.info("JMS Message Received!");
        log.info(helloWorldMessage.toString());
    }

    @JmsListener(destination = JmsConfig.SND_RCV_QUEUE)
    public void listenForHello(
            @Payload HelloWorldMessage helloWorldMessage,
            @Headers MessageHeaders headers,
            Message message
    ) throws JMSException {
        log.info("JMS Message Received!");
        log.info(helloWorldMessage.toString());

        final HelloWorldMessage messageReply = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Reply Message!")
                .build();


        jmsTemplate.convertAndSend( (Destination) message.getJMSReplyTo(), messageReply);
    }

}
