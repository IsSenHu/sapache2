package com.ssaw.smscenter.listen;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author HuSen
 * @date 2019/3/28 17:27
 */
@Slf4j
public class MessageListenerConcurrentlyImpl implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt messageExt : list) {
            try {
                String msg = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                log.info("接收到消息:{}", msg);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }
}