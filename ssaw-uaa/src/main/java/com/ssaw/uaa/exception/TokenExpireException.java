package com.ssaw.uaa.exception;

/**
 * @author HuSen
 * @date 2019/4/29 3:00
 */
public class TokenExpireException extends BaseTokenException {
    private static final long serialVersionUID = 9085043419968513414L;

    public TokenExpireException() {
    }

    public TokenExpireException(String message) {
        super(message);
    }
}