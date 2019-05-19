package com.ssaw.smscenter.model.message;

import com.ssaw.rocketmq.annotation.MessageKey;
import com.ssaw.rocketmq.annotation.RocketMqMessage;
import com.ssaw.smscenter.listen.TransactionListenerImpl;
import lombok.Data;

import java.io.Serializable;

/**
 * @author HuSen
 * @date 2019/3/29 13:30
 */
@Data
@RocketMqMessage(
        topicName = "test-message",
        tags = "test",
        sendTimeOut = 10000,
        groupName = "123456Group",
        instanceName = "123456",
        queueNums = 4,
        transaction = true,
        transactionListener = TransactionListenerImpl.class
)
public class MessageVO implements Serializable {
    private static final long serialVersionUID = -7913867025438686244L;

    @MessageKey
    private String username;

    private String password;
}