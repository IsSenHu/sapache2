package com.ssaw.ssawmehelper.dao.redis;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author HuSen
 * @date 2019/5/9 16:45
 */
@Repository
public class AutoCompletionDao {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public AutoCompletionDao(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void insert() {
        ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
        zSet.add("auto:completion", "aaa", 0);
        zSet.add("auto:completion", "aba", 0);
        zSet.add("auto:completion", "abc", 0);
        zSet.add("auto:completion", "abd", 0);
        zSet.add("auto:completion", "aca", 0);
        zSet.add("auto:completion", "acb", 0);
        zSet.add("auto:completion", "acc", 0);
        zSet.add("auto:completion", "acd", 0);
        zSet.add("auto:completion", "adb", 0);
    }

    public void get(String prefix) {
        String open;
        String close;
        String end = StringUtils.substring(prefix, prefix.length() - 1, prefix.length());
        if (end.equals("a")) {
            close = prefix + "{";
            open = prefix.substring(0, prefix.length() - 1) + "`";
        } else if (end.equals("b")) {
            close = prefix + "{";
            open = prefix.substring(0, prefix.length() - 1) + "a{";
        } else if (end.equals("c")) {
            close = prefix + "{";
            open = prefix.substring(0, prefix.length() - 1) + "b{";
        } else {
            close = prefix + "{";
            open = prefix.substring(0, prefix.length() - 1) + "c{";
        }
        ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
        Boolean addOpen = zSet.add("auto:completion", open, 0);
        Boolean addClose = zSet.add("auto:completion", close, 0);

        Long openRank = zSet.rank("auto:completion", open);
        Long closeRank = zSet.rank("auto:completion", close);
        if (closeRank - openRank == 1) {
            return;
        }
        Set<String> range = null;
        while (CollectionUtils.isEmpty(range)) {
            range = zSet.range("auto:completion", openRank + 1, closeRank - 1);
        }
        System.out.println(range);
        zSet.remove("auto:completion", open, close);
    }
}