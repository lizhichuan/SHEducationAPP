package com.shaohua.sheducation.request;

/**
 * Created by chuan on 2017/11/24.
 */

public class ChangePassBody {

    /**
     * password : string
     * passwordOld : string
     * passwordSecond : string
     */

    private String password;
    private String passwordOld;
    private String passwordSecond;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordSecond() {
        return passwordSecond;
    }

    public void setPasswordSecond(String passwordSecond) {
        this.passwordSecond = passwordSecond;
    }
}
