package com.shaohua.sheducation.request;

/**
 * Created by chuan on 2017/11/24.
 */

public class LoginBody {

    /**
     * mobile : string
     * password : string
     */

    private String mobile;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
