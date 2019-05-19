package com.ssaw.ssawstreamproducer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;

/**
 * @author HuSen
 * @date 2019/02/18
 */
@EnableBinding(Source.class)
public class SendService {

    private final Source source;

    @Autowired
    public SendService(Source source) {
        this.source = source;
    }

    public void sendMsg(String msg) {
        source.output().send(MessageBuilder.withPayload(msg).build());
    }
}
