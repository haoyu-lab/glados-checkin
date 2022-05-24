package com.example.gladoscheckin.common;

public class AjaxResult<T> {
    //状态码
    private int status;

    //响应提示信息
    private String message;

    //响应的实体
    private T body;

    //请求时间戳
    private long timestamp;

    public AjaxResult() {

    }

    private AjaxResult(int status, String message, T body) {
        this.status = status;
        this.message = message;
        this.body = body;
        this.timestamp = System.currentTimeMillis();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getBody() {
        return body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public static <T> AjaxResult<T> build(int status, String message, T body) {
        return new AjaxResult<T>(status, message, body);
    }

    public static <T> AjaxResult<T> build2Success(T body) {
        return build(Status.SUCCESS, Status.SUCCESS_MSG, body);
    }

    public static <T> AjaxResult<T> build2ServerError(T body) {
        return build(Status.SERVER_ERROR, Status.SERVER_ERROR_MSG, body);
    }

}
