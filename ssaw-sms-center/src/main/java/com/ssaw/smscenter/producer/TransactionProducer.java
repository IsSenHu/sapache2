package com.ssaw.smscenter.producer;

import com.ssaw.rocketmq.annotation.RocketMqProducer;
import com.ssaw.rocketmq.interfaces.BaseProducer;
import com.ssaw.smscenter.model.message.MessageVO;

/**
 * @author HuSen
 * @date 2019/3/28 17:17
 */
@RocketMqProducer
public interface TransactionProducer extends BaseProducer<MessageVO> {
}