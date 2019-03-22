package com.shaohua.sheducation.request;

/**
 * Created by chuan on 2017/11/25.
 */

public class EditMyInfoBody {

    /**
     * age : 0
     * avatar : string
     * education : 0
     * gender : 0
     * nickName : string
     * privacyOptions : 0
     */

    private int age;
    private String avatar;
    private int education;
    private int gender;
    private String nickName;
    private int privacyOptions;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPrivacyOptions() {
        return privacyOptions;
    }

    public void setPrivacyOptions(int privacyOptions) {
        this.privacyOptions = privacyOptions;
    }
}
