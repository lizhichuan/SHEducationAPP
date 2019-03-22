package com.shaohua.sheducation.resultbean;

/**
 * Created by chuan on 2017/9/14.
 */

public class FailVO {
    private boolean fail;

    public FailVO(boolean fail) {
        this.fail = fail;
    }

    public boolean isFail() {
        return fail;
    }

    public void setFail(boolean fail) {
        this.fail = fail;
    }
}
