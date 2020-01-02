package com.lc.ibps.elasticsearch.utils;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private static final long serialVersionUID = -6439955711249027143L;

    private int code ;
    private String msg ;
    private T data;

    public Result() {
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(T data) {
        this.code = 200;
        this.msg = "SUCCESS";
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "SUCCESS");
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> error() {
        return new Result<>(400, "error");
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
