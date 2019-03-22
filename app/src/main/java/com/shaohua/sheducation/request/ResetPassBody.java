package com.shaohua.sheducation.request;

/**
 * Created by chuan on 2017/11/24.
 */

public class ResetPassBody {


    /**
     * password : string
     * passwordSecond : string
     */

    private String password;
    private String passwordSecond;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSecond() {
        return passwordSecond;
    }

    public void setPasswordSecond(String passwordSecond) {
        this.passwordSecond = passwordSecond;
    }
}
