package com.ssaw.smscenter.listen;

import com.alibaba.fastjson.JSON;
import com.ssaw.smscenter.model.message.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;

/**
 * @author HuSen
 * @date 2019/3/28 17:20
 */
@Slf4j
public class TransactionListenerImpl implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            String msg = new String(message.getBody(), RemotingHelper.DEFAULT_CHARSET);
            MessageVO messageVO = JSON.parseObject(msg, MessageVO.class);
            log.info("执行本地事务:{}", message);
            if (StringUtils.equals(messageVO.getUsername(), "husen")) {
                return LocalTransactionState.COMMIT_MESSAGE;
            } else if (StringUtils.equals(messageVO.getUsername(), "233")) {
                return LocalTransactionState.UNKNOW;
            } else {
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        try {
            String msg = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
            MessageVO messageVO = JSON.parseObject(msg, MessageVO.class);
            log.info("进行事务回查:{}", msg);
            if (StringUtils.equals(messageVO.getUsername(), "233")) {
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }
}