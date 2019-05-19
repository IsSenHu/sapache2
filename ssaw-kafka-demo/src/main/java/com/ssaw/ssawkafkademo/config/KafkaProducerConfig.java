package com.ssaw.ssawkafkademo.config;

import com.ssaw.ssawkafkademo.executor.LogExecutor;
import com.ssaw.ssawkafkademo.properties.KafkaProducerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * @author HuSen
 * @date 2019/3/6 16:49
 */
@Configuration
@EnableKafka
@EnableConfigurationProperties(KafkaProducerProperties.class)
public class KafkaProducerConfig {

    public static LogExecutor logExecutor;

    private final KafkaProducerProperties properties;

    @PostConstruct
    public void init() {
        logExecutor = logExecutor();
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public KafkaProducerConfig(KafkaProducerProperties properties, KafkaTemplate<String, String> kafkaTemplate) {
        this.properties = properties;
        this.kafkaTemplate = kafkaTemplate;
    }

    private final KafkaTemplate<String, String> kafkaTemplate;

    public LogExecutor logExecutor() {
        Assert.hasText(properties.getAppName(), "应用名不能为空");
        return new LogExecutor(properties.getAppName(), kafkaTemplate);
    }
}