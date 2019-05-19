//package com.ssaw.ssawui.consumer;
//
//import com.ssaw.rocketmq.annotation.RocketMqConsumer;
//import com.ssaw.rocketmq.impl.BaseConsumerImpl;
//import com.ssaw.ssawui.listener.RequestLogMessageListenerConcurrently;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
//
///**
// * @author HuSen
// * @date 2019/3/28 14:27
// */
//@RocketMqConsumer(groupName = "requestLogConsumerGroup",
//        instanceName = "requestLogConsumer",
//        topicName = "ssaw-ui-log-request",
//        tags = "log",
//        consumeTimeOut = 10000,
//        messageModel = MessageModel.CLUSTERING,
//        consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET,
//        messageListenerConcurrently = RequestLogMessageListenerConcurrently.class)
//public class RequestLogConsumer extends BaseConsumerImpl {
//}