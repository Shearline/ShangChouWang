package com.jimmy.crowd.exception;

/**
 * 表示用户没有登录就登录受保护资源时抛出的异常
 */
public class AccessForbiddenException extends RuntimeException{
    static final long serialVersionUID = 1L;

    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
