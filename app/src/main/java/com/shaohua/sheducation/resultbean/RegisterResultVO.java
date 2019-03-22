package com.shaohua.sheducation.resultbean;

/**
 * Created by chuan on 2017/11/24.
 */

public class RegisterResultVO {

    /**
     * message : string
     * result : {"address":"string","age":0,"appPush":0,"avatar":"string","badges":[{"description":"string","id":0,"image":"string","name":"string","no":"string","status":0}],"education":0,"friendPush":0,"friendStatus":0,"gender":0,"id":0,"labels":[{"abbr":"string","id":0,"image":"string","name":"string","no":"string","parent":"string","status":0,"type":0}],"messageNum":0,"mobile":"string","nickName":"string","privacyOptions":0}
     * status : 0
     */

    private String message;
    private UserVO result;
    private int status;

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


    public UserVO getResult() {
        return result;
    }

    public void setResult(UserVO result) {
        this.result = result;
    }
}
