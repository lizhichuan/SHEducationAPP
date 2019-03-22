package com.shaohua.sheducation.request;

/**
 * Created by chuan on 2017/11/24.
 */

public class ValidCodeBody {

    /**
     * mobile : string
     * mobileVerifyCode : string
     */

    private String mobile;
    private String mobileVerifyCode;

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
}
