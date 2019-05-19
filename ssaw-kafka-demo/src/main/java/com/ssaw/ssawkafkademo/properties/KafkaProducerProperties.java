package com.ssaw.ssawkafkademo.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author HuSen
 * @date 2019/3/6 16:50
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "ssaw.log.kafka.producer")
public class KafkaProducerProperties {
    private String appName;
}