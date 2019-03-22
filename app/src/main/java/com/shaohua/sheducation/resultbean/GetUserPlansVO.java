package com.shaohua.sheducation.resultbean;

import java.util.List;

/**
 * Created by chuan on 2017/11/28.
 */

public class GetUserPlansVO {


    /**
     * message : string
     * result : {"count":0,"data":[{"completePercent":0,"createTime":0,"depositMode":0,"description":"string","id":0,"image":"string","label":{"abbr":"string","id":0,"image":"string","name":"string","no":"string","parent":"string","status":0,"type":0},"labelId":0,"members":[{"depositMoney":0,"finishStatus":0,"id":0,"milestoneFinishNum":0,"milestoneNum":0,"milestoneStatus":0,"payStatus":0,"planId":0,"refuseReason":"string","status":0,"testDest":"string","userId":0}],"name":"string","planMode":0,"status":0,"testDest":"string","testTime":0,"userId":0}],"page":0,"size":0}
     * status : 0
     */

    private String message;
    private ResultBean result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * count : 0
         * data : [{"completePercent":0,"createTime":0,"depositMode":0,"description":"string","id":0,"image":"string","label":{"abbr":"string","id":0,"image":"string","name":"string","no":"string","parent":"string","status":0,"type":0},"labelId":0,"members":[{"depositMoney":0,"finishStatus":0,"id":0,"milestoneFinishNum":0,"milestoneNum":0,"milestoneStatus":0,"payStatus":0,"planId":0,"refuseReason":"string","status":0,"testDest":"string","userId":0}],"name":"string","planMode":0,"status":0,"testDest":"string","testTime":0,"userId":0}]
         * page : 0
         * size : 0
         */

        private int count;
        private int page;
        private int size;
        private List<DataBean> data;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * completePercent : 0
             * createTime : 0
             * depositMode : 0
             * description : string
             * id : 0
             * image : string
             * label : {"abbr":"string","id":0,"image":"string","name":"string","no":"string","parent":"string","status":0,"type":0}
             * labelId : 0
             * members : [{"depositMoney":0,"finishStatus":0,"id":0,"milestoneFinishNum":0,"milestoneNum":0,"milestoneStatus":0,"payStatus":0,"planId":0,"refuseReason":"string","status":0,"testDest":"string","userId":0}]
             * name : string
             * planMode : 0
             * status : 0
             * testDest : string
             * testTime : 0
             * userId : 0
             */

            private int completePercent;
            private int createTime;
            private int depositMode;
            private String description;
            private int id;
            private String image;
            private LabelBean label;
            private int labelId;
            private String name;
            private int planMode;
            private int status;
            private String testDest;
            private int testTime;
            private int userId;
            private List<MembersBean> members;

            public int getCompletePercent() {
                return completePercent;
            }

            public void setCompletePercent(int completePercent) {
                this.completePercent = completePercent;
            }

            public int getCreateTime() {
                return createTime;
            }

            public void setCreateTime(int createTime) {
                this.createTime = createTime;
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

            public LabelBean getLabel() {
                return label;
            }

            public void setLabel(LabelBean label) {
                this.label = label;
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

            public int getTestTime() {
                return testTime;
            }

            public void setTestTime(int testTime) {
                this.testTime = testTime;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public List<MembersBean> getMembers() {
                return members;
            }

            public void setMembers(List<MembersBean> members) {
                this.members = members;
            }

            public static class LabelBean {
                /**
                 * abbr : string
                 * id : 0
                 * image : string
                 * name : string
                 * no : string
                 * parent : string
                 * status : 0
                 * type : 0
                 */

                private String abbr;
                private int id;
                private String image;
                private String name;
                private String no;
                private String parent;
                private int status;
                private int type;

                public String getAbbr() {
                    return abbr;
                }

                public void setAbbr(String abbr) {
                    this.abbr = abbr;
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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getNo() {
                    return no;
                }

                public void setNo(String no) {
                    this.no = no;
                }

                public String getParent() {
                    return parent;
                }

                public void setParent(String parent) {
                    this.parent = parent;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }
            }

            public static class MembersBean {
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
        }
    }
}
