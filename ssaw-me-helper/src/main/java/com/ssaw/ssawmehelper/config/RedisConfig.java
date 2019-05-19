package com.ssaw.ssawmehelper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author HuSen
 * @date 2019/4/19 13:35
 */
@Configuration
public class RedisConfig {

    @Autowired
    public RedisConfig(StringRedisTemplate stringRedisTemplate) {
        stringRedisTemplate.setEnableTransactionSupport(true);
    }
}