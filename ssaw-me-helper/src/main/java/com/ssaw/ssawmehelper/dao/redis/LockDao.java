package com.ssaw.ssawmehelper.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;

/**
 * @author HuSen
 * @date 2019/5/9 17:23
 */
@Repository
public class LockDao {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public LockDao(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void lock(String key) throws InterruptedException {
        // 不超时的实现
        Boolean aBoolean = null;
        while (Objects.isNull(aBoolean) || !aBoolean) {
            aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(key, UUID.randomUUID().toString());
            Thread.sleep(100);
        }
    }

    public void unlock(String key) {
        // 先根据UUID来判断值是否已被修改 被修改了 则说明被其他锁拿到了
        stringRedisTemplate.watch(key);
        stringRedisTemplate.multi();
        stringRedisTemplate.delete(key);
        stringRedisTemplate.exec();
    }
}