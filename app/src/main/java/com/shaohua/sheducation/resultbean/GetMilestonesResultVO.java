package com.shaohua.sheducation.resultbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chuan on 2017/11/28.
 */

public class GetMilestonesResultVO implements Serializable {

    /**
     * message : string
     * result : [{"finishTime":0,"id":0,"my":{"alarmTime":"2017-11-28T00:47:12.231Z","auditOperator":0,"clickTime":"2017-11-28T00:47:12.231Z","id":0,"image":"string","milestoneId":0,"planId":0,"reportDesc":"string","reportId":0,"reportStatus":0,"status":0,"userId":0},"name":"string","other":{"alarmTime":"2017-11-28T00:47:12.231Z","auditOperator":0,"clickTime":"2017-11-28T00:47:12.231Z","id":0,"image":"string","milestoneId":0,"planId":0,"reportDesc":"string","reportId":0,"reportStatus":0,"status":0,"userId":0},"planId":0}]
     * status : 0
     */

    private String message;
    private int status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * finishTime : 0
         * id : 0
         * my : {"alarmTime":"2017-11-28T00:47:12.231Z","auditOperator":0,"clickTime":"2017-11-28T00:47:12.231Z","id":0,"image":"string","milestoneId":0,"planId":0,"reportDesc":"string","reportId":0,"reportStatus":0,"status":0,"userId":0}
         * name : string
         * other : {"alarmTime":"2017-11-28T00:47:12.231Z","auditOperator":0,"clickTime":"2017-11-28T00:47:12.231Z","id":0,"image":"string","milestoneId":0,"planId":0,"reportDesc":"string","reportId":0,"reportStatus":0,"status":0,"userId":0}
         * planId : 0
         */

        private int finishTime;
        private int id;
        private MyBean my;
        private String name;
        private OtherBean other;
        private int planId;

        public int getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(int finishTime) {
            this.finishTime = finishTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public MyBean getMy() {
            return my;
        }

        public void setMy(MyBean my) {
            this.my = my;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public OtherBean getOther() {
            return other;
        }

        public void setOther(OtherBean other) {
            this.other = other;
        }

        public int getPlanId() {
            return planId;
        }

        public void setPlanId(int planId) {
            this.planId = planId;
        }

        public static class MyBean {
            /**
             * alarmTime : 2017-11-28T00:47:12.231Z
             * auditOperator : 0
             * clickTime : 2017-11-28T00:47:12.231Z
             * id : 0
             * image : string
             * milestoneId : 0
             * planId : 0
             * reportDesc : string
             * reportId : 0
             * reportStatus : 0
             * status : 0
             * userId : 0
             */

            private String alarmTime;
            private int auditOperator;
            private String clickTime;
            private int id;
            private String image;
            private int milestoneId;
            private int planId;
            private String reportDesc;
            private int reportId;
            private int reportStatus;
            private int status;
            private int userId;

            public String getAlarmTime() {
                return alarmTime;
            }

            public void setAlarmTime(String alarmTime) {
                this.alarmTime = alarmTime;
            }

            public int getAuditOperator() {
                return auditOperator;
            }

            public void setAuditOperator(int auditOperator) {
                this.auditOperator = auditOperator;
            }

            public String getClickTime() {
                return clickTime;
            }

            public void setClickTime(String clickTime) {
                this.clickTime = clickTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getMilestoneId() {
                return milestoneId;
            }

            public void setMilestoneId(int milestoneId) {
                this.milestoneId = milestoneId;
            }

            public int getPlanId() {
                return planId;
            }

            public void setPlanId(int planId) {
                this.planId = planId;
            }

            public String getReportDesc() {
                return reportDesc;
            }

            public void setReportDesc(String reportDesc) {
                this.reportDesc = reportDesc;
            }

            public int getReportId() {
                return reportId;
            }

            public void setReportId(int reportId) {
                this.reportId = reportId;
            }

            public int getReportStatus() {
                return reportStatus;
            }

            public void setReportStatus(int reportStatus) {
                this.reportStatus = reportStatus;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }

        public static class OtherBean {
            /**
             * alarmTime : 2017-11-28T00:47:12.231Z
             * auditOperator : 0
             * clickTime : 2017-11-28T00:47:12.231Z
             * id : 0
             * image : string
             * milestoneId : 0
             * planId : 0
             * reportDesc : string
             * reportId : 0
             * reportStatus : 0
             * status : 0
             * userId : 0
             */

            private String alarmTime;
            private int auditOperator;
            private String clickTime;
            private int id;
            private String image;
            private int milestoneId;
            private int planId;
            private String reportDesc;
            private int reportId;
            private int reportStatus;
            private int status;
            private int userId;

            public String getAlarmTime() {
                return alarmTime;
            }

            public void setAlarmTime(String alarmTime) {
                this.alarmTime = alarmTime;
            }

            public int getAuditOperator() {
                return auditOperator;
            }

            public void setAuditOperator(int auditOperator) {
                this.auditOperator = auditOperator;
            }

            public String getClickTime() {
                return clickTime;
            }

            public void setClickTime(String clickTime) {
                this.clickTime = clickTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getMilestoneId() {
                return milestoneId;
            }

            public void setMilestoneId(int milestoneId) {
                this.milestoneId = milestoneId;
            }

            public int getPlanId() {
                return planId;
            }

            public void setPlanId(int planId) {
                this.planId = planId;
            }

            public String getReportDesc() {
                return reportDesc;
            }

            public void setReportDesc(String reportDesc) {
                this.reportDesc = reportDesc;
            }

            public int getReportId() {
                return reportId;
            }

            public void setReportId(int reportId) {
                this.reportId = reportId;
            }

            public int getReportStatus() {
                return reportStatus;
            }

            public void setReportStatus(int reportStatus) {
                this.reportStatus = reportStatus;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
