package com.shaohua.sheducation.resultbean;

/**
 * Created by chuan on 2017/12/3.
 */

public class PlanMemberVO {

    /**
     * depositMoney : 0
     * finishStatus : 0
     * id : 0
     * milestoneFinishNum : 0
     * milestoneNum : 0
     * milestoneStatus : 0
     * payStatus : 0
     * planId : 0
     * refuseReason : string
     * status : 0
     * testDest : string
     * userId : 0
     */

    private int depositMoney;
    private int finishStatus;
    private int id;
    private int milestoneFinishNum;
    private int milestoneNum;
    private int milestoneStatus;
    private int payStatus;
    private int planId;
    private String refuseReason;
    private int status;
    private String testDest;
    private int userId;
    private String avatar;
    private String nickName;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getDepositMoney() {
        return depositMoney;
    }

    public void setDepositMoney(int depositMoney) {
        this.depositMoney = depositMoney;
    }

    public int getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(int finishStatus) {
        this.finishStatus = finishStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMilestoneFinishNum() {
        return milestoneFinishNum;
    }

    public void setMilestoneFinishNum(int milestoneFinishNum) {
        this.milestoneFinishNum = milestoneFinishNum;
    }

    public int getMilestoneNum() {
        return milestoneNum;
    }

    public void setMilestoneNum(int milestoneNum) {
        this.milestoneNum = milestoneNum;
    }

    public int getMilestoneStatus() {
        return milestoneStatus;
    }

    public void setMilestoneStatus(int milestoneStatus) {
        this.milestoneStatus = milestoneStatus;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTestDest() {
        return testDest;
    }

    public void setTestDest(String testDest) {
        this.testDest = testDest;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
