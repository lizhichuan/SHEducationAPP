package com.shaohua.sheducation.request;

/**
 * Created by chuan on 2017/11/24.
 */

public class RegisterBody {

    /**
     * mobile : string
     * mobileVerifyCode : string
     * password : string
     */

    private String mobile;
    private String mobileVerifyCode;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileVerifyCode() {
        return mobileVerifyCode;
    }

    public void setMobileVerifyCode(String mobileVerifyCode) {
        this.mobileVerifyCode = mobileVerifyCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
