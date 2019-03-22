package com.shaohua.sheducation.request;

import java.util.List;

/**
 * Created by chuan on 2017/11/28.
 */

public class FriendSettingBody {

    /**
     * age : 0
     * distance : 0
     * education : 0
     * endTime : 2017-11-28T00:47:11.909Z
     * gender : 0
     * labels : [0]
     * pushStatus : 0
     * startTime : 2017-11-28T00:47:11.909Z
     * testRange : 0
     */

    private int age;
    private int distance;
    private int education;
    private String endTime;
    private int gender;
    private int pushStatus;
    private String startTime;
    private int testRange;
    private List<Integer> labels;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getTestRange() {
        return testRange;
    }

    public void setTestRange(int testRange) {
        this.testRange = testRange;
    }

    public List<Integer> getLabels() {
        return labels;
    }

    public void setLabels(List<Integer> labels) {
        this.labels = labels;
    }
}
