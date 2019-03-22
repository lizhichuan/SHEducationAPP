package com.shaohua.sheducation.request;

/**
 * Created by chuan on 2017/11/28.
 */

public class MilestoneAddBody {

    /**
     * finishTime : 2017-11-28T00:47:12.253Z
     * name : string
     * planId : 0
     */

    private String finishTime;
    private String name;
    private int planId;

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }
}
