package com.zxc.exception;

/**
 * Created by zxc on 16/7/12.
 * Spring事务处理只能捕获运行时异常
 * */
public class SeckillException extends RuntimeException{

    public SeckillException(String message) {
        super(message);
    }

    protected SeckillException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
