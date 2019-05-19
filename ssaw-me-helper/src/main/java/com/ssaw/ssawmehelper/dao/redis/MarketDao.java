package com.ssaw.ssawmehelper.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author HuSen
 * @date 2019/5/8 16:57
 */
@Repository
public class MarketDao {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public MarketDao(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void watch(String name) {
        stringRedisTemplate.watch("HuSen:1");

        stringRedisTemplate.multi();
        stringRedisTemplate.opsForValue().set("HuSen:1", name);
        stringRedisTemplate.exec();
    }
}