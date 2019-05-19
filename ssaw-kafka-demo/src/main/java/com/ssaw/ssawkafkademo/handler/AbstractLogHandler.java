package com.ssaw.ssawkafkademo.handler;

import com.ssaw.ssawkafkademo.config.KafkaProducerConfig;
import com.ssaw.ssawkafkademo.vo.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * @author HuSen
 * @date 2019/3/7 13:21
 */
@Slf4j
public abstract class AbstractLogHandler {

    /**
     * 格式化日志为字符串
     * @param log 日志对象
     * @return 格式化后的字符串
     */
    abstract String formatLog(Log log);

    /**
     * 设置日志的基本信息
     * @param logType 日志类型
     * @param o 数据
     * @return 日志对象
     */
    abstract Log setLogBaseInfo(String logType, Object o);

    /**
     * 设置日志的基本信息
     * @param logType 日志类型
     * @param o 数据
     * @param message 信息
     * @return 日志对象
     */
    abstract Log setLogBaseInfo(String logType, Object o, String message);

    public void log(Object o, String logType) {
        KafkaProducerConfig.logExecutor.sendLog(formatLog(setLogBaseInfo(logType, o)));
    }

    public void log(Object o, String logType, String message) {
        KafkaProducerConfig.logExecutor.sendLog(formatLog(setLogBaseInfo(logType, o, message)));
    }
}