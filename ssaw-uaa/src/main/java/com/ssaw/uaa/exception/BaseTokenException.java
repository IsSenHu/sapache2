package com.ssaw.uaa.exception;

/**
 * @author HuSen
 * @date 2019/4/29 2:55
 */
public class BaseTokenException extends Exception {
    private static final long serialVersionUID = -6396814866080635244L;

    BaseTokenException() {
    }

    BaseTokenException(String message) {
        super(message);
    }
}