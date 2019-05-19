package com.ssaw.ssawauthenticatecenterfeign.event.local;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author HuSen
 * @date 2019/2/27 13:38
 */
@Getter
@Setter
public class AppFinishedEvent extends ApplicationEvent {
    private static final long serialVersionUID = -8648898486117627862L;

    public AppFinishedEvent(Object source) {
        super(source);
    }
}