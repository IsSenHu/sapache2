package com.ssaw.ssawmehelper;

import com.ssaw.redis.lock.RedisLock;
import com.ssaw.redis.lock.RedisSemaphore;
import com.ssaw.ssawmehelper.dao.redis.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SsawMeHelperApplicationTests {

    @Autowired
    private KaoQinDao kaoQinDao;

    @Autowired
    private MyCollectionDao myCollectionDao;

    @Autowired
    private GoodsDemoDao goodsDemoDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MarketDao marketDao;

    @Autowired
    private LogDao logDao;

    @Autowired
    private AutoCompletionDao autoCompletionDao;

    @Autowired
    private LockDao lockDao;

    @Test
    public void contextLoads() {
        stringRedisTemplate.opsForValue().set("sentinel", "test");
        System.out.println(stringRedisTemplate.opsForValue().get("sentinel"));
    }

    @Test
    public void demo2() {
        marketDao.watch("你好");
    }

    @Test
    public void demo3() {
        autoCompletionDao.get("ac");
    }

    @Test
    public void demo4() throws InterruptedException {
        lockDao.lock("husen");
        lockDao.unlock("husen");
    }

    @Test
    public void demo5() throws InterruptedException {
        lockDao.lock("husen");
        lockDao.unlock("husen");
    }

    @Test
    public void demo6() throws InterruptedException {
        RedisSemaphore semaphore = new RedisSemaphore(stringRedisTemplate, 2);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                String acquire = semaphore.acquire("semaphore:key");
                System.out.println(acquire);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Objects.nonNull(acquire)) {
                    semaphore.release("semaphore:key", acquire);
                }
            }).start();
        }
        Thread.sleep(1000000L);
    }

    @Test
    public void demo7() {
        stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.zAdd("233".getBytes(), System.currentTimeMillis(), "333".getBytes());
            Long aLong = connection.zRank("233".getBytes(), "333".getBytes());
            List<Object> objects = connection.closePipeline();
            return null;
        });
    }

    @Test
    public void demo8() throws InterruptedException {
        RedisLock redisLock = new RedisLock(stringRedisTemplate);
        boolean asdedddd = redisLock.lock("asdedddd", 100, TimeUnit.SECONDS);
        System.out.println(asdedddd);
        redisLock.unlock("asdedddd");
        Thread.sleep(1000000L);
    }

    @Test
    public void demo9() throws InterruptedException {
        RedisLock redisLock = new RedisLock(stringRedisTemplate);
        boolean asdedddd = redisLock.lock("asdedddd", 100, TimeUnit.SECONDS);
        System.out.println(asdedddd);
        redisLock.unlock("asdedddd");
        Thread.sleep(1000000L);
    }

    @Test
    public void demo10() {
        ListOperations<String, String> list = stringRedisTemplate.opsForList();
        list.rightPushAll("queue1", "husen", "senhu");
        list.rightPushAll("queue2", "husen2", "senhu2");
        list.rightPushAll("queue3", "husen3", "senhu3");
    }

    @Test
    public void demo11() {
        while (true) {
            Object execute = stringRedisTemplate.execute((RedisCallback<String>) connection -> {
                List<byte[]> bytes = connection.bLPop(30, "queue1".getBytes(), "queue2".getBytes(), "queue3".getBytes());
                return new String(bytes.get(1));
            });
            System.out.println(execute);
        }
    }
}
