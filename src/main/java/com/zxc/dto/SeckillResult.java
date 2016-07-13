package com.zxc.dto;

/**
 * Created by zxc on 16/7/13.
 */
public class SeckillResult<T> {
    private T data;
    private boolean success;
    private String msg;

    public SeckillResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public SeckillResult(boolean success, T data) {
        this.data = data;
        this.success = success;
    }

    public T getData() {

        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
