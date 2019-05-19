package com.ssaw.ssawmehelper.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

/**
 * 计数器
 *
 * @author HuSen
 * @date 2019/5/9 13:12
 */
@Repository
public class CountDao {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public CountDao(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void count() {
        HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
        ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
    }
}