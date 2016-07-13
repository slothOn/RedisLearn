package com.zxc.exception;

/**
 * Created by zxc on 16/7/12.
 */
public class SeckillCloseException extends SeckillException {


    public SeckillCloseException(String message) {
        super(message);
    }

    protected SeckillCloseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
