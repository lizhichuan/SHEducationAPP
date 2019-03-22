package com.shaohua.sheducation.resultbean;

/**
 * Created by chuan on 2017/11/25.
 */

public class EditMyInfoResultVO {

    /**
     * message : string
     * result : false
     * status : 0
     */

    private String message;
    private boolean result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
