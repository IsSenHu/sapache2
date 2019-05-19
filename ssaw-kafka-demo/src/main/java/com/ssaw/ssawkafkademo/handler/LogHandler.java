package com.ssaw.ssawkafkademo.handler;

import com.alibaba.fastjson.JSON;
import com.ssaw.ssawkafkademo.vo.Log;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author HuSen
 * @date 2019/3/6 16:56
 */
@Component
@AllArgsConstructor
public class LogHandler extends AbstractLogHandler {

    @Override
    String formatLog(Log log) {
        return JSON.toJSONString(log);
    }

    @Override
    Log setLogBaseInfo(String logType, Object o) {
        Log log = new Log();
        log.setType(logType);
        log.setLog(o);
        return log;
    }

    @Override
    Log setLogBaseInfo(String logType, Object o, String message) {
        Log log = new Log();
        log.setType(logType);
        log.setLog(o);
        log.setMessage(message);
        return log;
    }
}