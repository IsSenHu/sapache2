//package com.ssaw.ssawui.listener;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//import org.springframework.util.CollectionUtils;
//
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
///**
// * @author HuSen
// * @date 2019/3/28 14:30
// */
//@Slf4j
//public class RequestLogMessageListenerConcurrently implements MessageListenerConcurrently {
//
//    @Override
//    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//        String topic = consumeConcurrentlyContext.getMessageQueue().getTopic();
//        int queueId = consumeConcurrentlyContext.getMessageQueue().getQueueId();
//        String brokerName = consumeConcurrentlyContext.getMessageQueue().getBrokerName();
//
//        if (!CollectionUtils.isEmpty(list)) {
//            for (MessageExt messageExt : list) {
//                try {
//                    log.info("Broker:<{}> Topic:<{}> QueueId:<{}>---接收到请求和认证结果:[{}]", brokerName, topic, queueId, new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET));
//                } catch (UnsupportedEncodingException e) {
//                    log.error("解析byte[]失败:", e);
//                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//                }
//            }
//        }
//        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//    }
//}