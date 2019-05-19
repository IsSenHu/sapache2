package com.ssaw.uaa.exception;

/**
 * @author HuSen
 * @date 2019/4/29 2:57
 */
public class TokenErrorException extends BaseTokenException {
    private static final long serialVersionUID = -1052537471601927110L;

    public TokenErrorException() {
    }

    public TokenErrorException(String message) {
        super(message);
    }
}