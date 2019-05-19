package com.ssaw.ssawauthenticatecenterfeign.event.local;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 上传作用域与白名单完成事件
 * @author HuSen
 * @date 2019/3/2 14:08
 */
@Getter
@Setter
public class UploadScopeAndWhiteListFinishedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 2198811383831036993L;

    public UploadScopeAndWhiteListFinishedEvent(Object source) {
        super(source);
    }
}