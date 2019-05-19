package com.ssaw.ssawstreamhello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author HuSen.
 * @date 2018/11/20 15:52.
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@EnableScheduling
@SpringBootApplication
public class StreamHelloApplication {

    private final MessageChannel sender;

    @Autowired
    public StreamHelloApplication(@Qualifier("sender") MessageChannel sender) {
        this.sender = sender;
    }

    public static void main(String[] args) {
        SpringApplication.run(StreamHelloApplication.class, args);
    }

    @Scheduled(fixedDelay = 3000L)
    public void send() {
        sender.send(MessageBuilder.withPayload("From MessageChannel").build());
    }
}
