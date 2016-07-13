package com.zxc.exception;

import com.zxc.dto.SeckillExecution;

/**
 * Created by zxc on 16/7/12.
 */
public class RepeatkillException extends SeckillException {

    public RepeatkillException(String message) {
        super(message);
    }

    protected RepeatkillException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
