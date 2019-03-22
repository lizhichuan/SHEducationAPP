package com.shaohua.sheducation.resultbean;

import com.shaohua.sheducation.resultbean.GetSystemLabelsResultVO;

import java.io.Serializable;

/**
 * Created by chuan on 2017/11/24.
 */

public class FilterPlanBean implements Serializable {
    private GetSystemLabelsResultVO.ResultBean getSystemLabelsVO;
    private int planMode = 0;
    private int depositMode = -1;
    private int status = -1;

    public int getPlanMode() {
        return planMode;
    }

    public void setPlanMode(int planMode) {
        this.planMode = planMode;
    }

    public int getDepositMode() {
        return depositMode;
    }

    public void setDepositMode(int depositMode) {
        this.depositMode = depositMode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public GetSystemLabelsResultVO.ResultBean getGetSystemLabelsVO() {
        return getSystemLabelsVO;
    }

    public void setGetSystemLabelsVO(GetSystemLabelsResultVO.ResultBean getSystemLabelsVO) {
        this.getSystemLabelsVO = getSystemLabelsVO;
    }
}
