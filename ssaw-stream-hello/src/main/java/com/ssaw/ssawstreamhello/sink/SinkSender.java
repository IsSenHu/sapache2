package com.ssaw.ssawstreamhello.sink;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * @author HuSen.
 * @date 2018/11/20 15:52.
 */
public interface SinkSender {

    String INPUT = "sender";

    @Output(SinkSender.INPUT)
    MessageChannel output();
}
