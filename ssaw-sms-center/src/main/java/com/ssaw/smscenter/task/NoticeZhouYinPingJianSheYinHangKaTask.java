package com.ssaw.smscenter.task;

import com.ssaw.commons.util.sms.AliYunSmsUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author HuSen.
 * @date 2019/1/13 4:36.
 */
@Component
public class NoticeZhouYinPingJianSheYinHangKaTask {

    public volatile boolean me = true;
    public volatile boolean stop = true;

    @Scheduled(fixedDelay = 3600000, initialDelay = 5000)
    public void notice() {
        if(stop) {
            return;
        }
        if(me) {
            AliYunSmsUtil.send("LTAI07hchIXDO0ZY", "d08PwCrtoiQGjroNo8IoxdQ4OhuxXO", "14780118635", "爱动网", "SMS_155455863", "{\"name\":\"" + "周银萍" + "\"}");
        } else {
            AliYunSmsUtil.send("LTAI07hchIXDO0ZY", "d08PwCrtoiQGjroNo8IoxdQ4OhuxXO", "15828301179", "爱动网", "SMS_155455863", "{\"name\":\"" + "周银萍" + "\"}");
        }
    }
}
