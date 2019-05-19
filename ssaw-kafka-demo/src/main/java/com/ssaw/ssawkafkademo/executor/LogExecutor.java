package com.ssaw.ssawkafkademo.executor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author HuSen
 * @date 2019/3/6 16:56
 */
@Slf4j
@AllArgsConstructor
public class LogExecutor {

    private String topic;

    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendLog(String log) {
        kafkaTemplate.send(topic, log);
    }
}