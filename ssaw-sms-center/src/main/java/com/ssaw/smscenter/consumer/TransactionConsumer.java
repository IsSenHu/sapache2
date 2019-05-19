package com.ssaw.smscenter.consumer;

import com.ssaw.rocketmq.annotation.RocketMqConsumer;
import com.ssaw.rocketmq.impl.BaseConsumerImpl;
import com.ssaw.smscenter.listen.MessageListenerConcurrentlyImpl;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * @author HuSen
 * @date 2019/3/28 17:24
 */
@RocketMqConsumer(
        groupName = "testTransactionConsumerGroup",
        instanceName = "testTransactionConsumer",
        topicName = "test-message",
        tags = "test",
        consumeTimeOut = 10000,
        messageModel = MessageModel.CLUSTERING,
        consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET,
        messageListenerConcurrently = MessageListenerConcurrentlyImpl.class
)
public class TransactionConsumer extends BaseConsumerImpl {
}