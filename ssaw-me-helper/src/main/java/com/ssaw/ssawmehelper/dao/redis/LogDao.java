package com.ssaw.ssawmehelper.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * 日志
 *
 * @author HuSen
 * @date 2019/5/9 11:16
 */
@Repository
public class LogDao {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public LogDao(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void recent(String... log) {
        ListOperations<String, String> list = stringRedisTemplate.opsForList();
        list.leftPushAll("recent:log", log);
        list.trim("recent:log", 0, 99);
    }
}