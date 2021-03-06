package tech.dimitar.jms.demo.jmsdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public static final String MY_QUEUE_NAME = "my_queue";
    public static final String SND_RCV_QUEUE= "reply_back_to_me";

    /**
     * Bean -> message converter
     * Using Jackson libraries to convert message to JSON
     * ie Object -> To JSON String and vice versa, JSON String -> To Java Object
     * @return converter
     */
    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        return converter;
    }

}
