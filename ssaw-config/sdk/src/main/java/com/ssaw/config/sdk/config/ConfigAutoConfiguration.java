package com.ssaw.config.sdk.config;

import com.ssaw.config.sdk.annotation.EnableConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuSen
 * @date 2019/5/30 17:04
 */
@Configuration
@EnableFeignClients(basePackages = "com.ssaw.config.sdk")
@ConditionalOnBean(annotation = EnableConfig.class)
public class ConfigAutoConfiguration {

}