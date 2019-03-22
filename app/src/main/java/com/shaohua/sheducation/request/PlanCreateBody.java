package com.shaohua.sheducation.request;

/**
 * Created by chuan on 2017/11/25.
 */

public class PlanCreateBody {

    /**
     * customLabel : string
     * depositMode : 0
     * description : string
     * image : string
     * labelId : 0
     * name : string
     * planMode : 0
     * testDest : string
     * testTime : 2017-11-24T02:16:35.560Z
     */

    private String customLabel;
    private int depositMode;
    private String description;
    private String image;
    private int labelId;
    private String name;
    private int planMode;
    private String testDest;
    private String testTime;

    public String getCustomLabel() {
        return customLabel;
    }

    public void setCustomLabel(String customLabel) {
        this.customLabel = customLabel;
    }

    public int getDepositMode() {
        return depositMode;
    }

    public void setDepositMode(int depositMode) {
        this.depositMode = depositMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlanMode() {
        return planMode;
    }

    public void setPlanMode(int planMode) {
        this.planMode = planMode;
    }

    public String getTestDest() {
        return testDest;
    }

    public void setTestDest(String testDest) {
        this.testDest = testDest;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }
}
