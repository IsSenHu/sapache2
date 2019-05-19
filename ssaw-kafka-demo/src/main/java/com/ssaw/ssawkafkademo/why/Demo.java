package com.ssaw.ssawkafkademo.why;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

/**
 * @author HuSen
 * @date 2019/3/6 11:07
 */
public class Demo {

    /** 索引 */
    private static final Map<Integer, Map<Integer, Integer>> INDEX = new HashMap<>();

    /** 文件存储 */
    private static final Map<Integer, List<Message>> LOG = new HashMap<>();

    private static final int MAX_SIZE = 100000;

    private static final int PARTITION_NUMBER = 50;

    public static void main(String[] args) {

        for (int i = 0; i < PARTITION_NUMBER; i++) {
            LOG.put(i, new ArrayList<>());
            INDEX.put(i, new HashMap<>(2000));
        }

        for (int offset = 0; offset < MAX_SIZE; offset++) {
            // 先分段
            int i = offset / 2000;
            // 存储数据
            Message message = new Message(offset, "我是消息:" + offset);
            LOG.get(i).add(message);
            // 构建索引
            INDEX.get(i).put(message.hashCode(), offset - 2000 * i);
        }

        // 查询第97876条数据 唯一索引
        int offset = 97876;
        int partitionNo = offset / 2000;
        List<Message> messages = LOG.get(partitionNo);
        Map<Integer, Integer> index = INDEX.get(partitionNo);
        Message message = messages.get(index.get(offset));
        System.out.println(message);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    private static class Message {
        private Integer offset;
        private String value;
    }
}