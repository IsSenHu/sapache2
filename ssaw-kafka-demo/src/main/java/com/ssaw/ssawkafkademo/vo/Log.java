package com.ssaw.ssawkafkademo.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 日志对象
 * @author HuSen
 * @date 2019/3/7 13:15
 */
@Getter
@Setter
public class Log implements Serializable {
    private static final long serialVersionUID = 1694239092679630716L;

    /** 日志类型 */
    private String type;

    private String message;

    private Object log;
}