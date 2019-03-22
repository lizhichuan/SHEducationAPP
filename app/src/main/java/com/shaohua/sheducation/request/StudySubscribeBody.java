package com.shaohua.sheducation.request;

import java.util.List;

/**
 * Created by chuan on 2017/11/28.
 */

public class StudySubscribeBody {

    /**
     * labels : [0]
     * q : string
     */

    private String q;
    private List<Integer> labels;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public List<Integer> getLabels() {
        return labels;
    }

    public void setLabels(List<Integer> labels) {
        this.labels = labels;
    }
}
