package com.njcool.lzccommon.network.http.mode;

/**
 * Created by chuan on 2017/8/30.
 */

public class BaseResulty<T> {
    int status;
    String message;

    T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
