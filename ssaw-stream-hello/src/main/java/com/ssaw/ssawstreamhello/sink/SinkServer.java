package com.ssaw.ssawstreamhello.sink;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import java.io.UnsupportedEncodingException;

/**
 * @author HuSen.
 * @date 2018/11/20 15:52.
 */
@Slf4j
@EnableBinding(value = {SinkSender.class})
public class SinkServer {

    @StreamListener(SinkSender.INPUT)
    public void receive(Object payload) throws UnsupportedEncodingException {
        log.info("Received:{}", new String((byte[]) payload, "UTF-8"));
    }
}
