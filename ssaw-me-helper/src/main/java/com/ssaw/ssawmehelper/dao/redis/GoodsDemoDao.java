package com.ssaw.ssawmehelper.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.UUID;

/**
 * @author HuSen
 * @date 2019/4/24 15:08
 */
@Repository
public class GoodsDemoDao {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String LOGIN = "login_goods";

    private static final String VIEWED_USER = "viewed_user";

    private static final String VIEWED_GOODS_PREFIX = "viewed_goods:";

    @Autowired
    public GoodsDemoDao(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String login(Long userId) {
        HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
        String token = UUID.randomUUID().toString();
        hash.put(LOGIN, token, userId.toString());
        return token;
    }

    public Long isLogin(String token) {
        HashOperations<String, String, String> hash = stringRedisTemplate.opsForHash();
        return Long.valueOf(Objects.requireNonNull(hash.get(LOGIN, token)));
    }

    public void insertViewedUser(Long userID) {
        ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
        zSet.add(VIEWED_USER, String.valueOf(userID), System.currentTimeMillis());
    }

    public void insertViewedGoods(String goodsBn, Long userId) {
        ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
        zSet.add(VIEWED_GOODS_PREFIX.concat(String.valueOf(userId)), goodsBn, System.currentTimeMillis());
    }
}