package com.shaohua.sheducation.resultbean;

/**
 * Created by chuan on 2017/11/29.
 */

public class PlanExitVO {

    /**
     * message : string
     * result : {"badge":{"description":"string","id":0,"image":"string","name":"string","no":"string","status":0},"badgeId":0,"depositMoney":0,"finishStatus":0,"image":"string","milestoneFinishNum":0,"milestoneNum":0,"rewardCoins":0,"rewardMoney":0,"rewardType":0,"scoreStatus":0,"userId":0}
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
         * badge : {"description":"string","id":0,"image":"string","name":"string","no":"string","status":0}
         * badgeId : 0
         * depositMoney : 0
         * finishStatus : 0
         * image : string
         * milestoneFinishNum : 0
         * milestoneNum : 0
         * rewardCoins : 0
         * rewardMoney : 0
         * rewardType : 0
         * scoreStatus : 0
         * userId : 0
         */

        private BadgeBean badge;
        private int badgeId;
        private int depositMoney;
        private int finishStatus;
        private String image;
        private int milestoneFinishNum;
        private int milestoneNum;
        private int rewardCoins;
        private int rewardMoney;
        private int rewardType;
        private int scoreStatus;
        private int userId;

        public BadgeBean getBadge() {
            return badge;
        }

        public void setBadge(BadgeBean badge) {
            this.badge = badge;
        }

        public int getBadgeId() {
            return badgeId;
        }

        public void setBadgeId(int badgeId) {
            this.badgeId = badgeId;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public int getRewardCoins() {
            return rewardCoins;
        }

        public void setRewardCoins(int rewardCoins) {
            this.rewardCoins = rewardCoins;
        }

        public int getRewardMoney() {
            return rewardMoney;
        }

        public void setRewardMoney(int rewardMoney) {
            this.rewardMoney = rewardMoney;
        }

        public int getRewardType() {
            return rewardType;
        }

        public void setRewardType(int rewardType) {
            this.rewardType = rewardType;
        }

        public int getScoreStatus() {
            return scoreStatus;
        }

        public void setScoreStatus(int scoreStatus) {
            this.scoreStatus = scoreStatus;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public static class BadgeBean {
            /**
             * description : string
             * id : 0
             * image : string
             * name : string
             * no : string
             * status : 0
             */

            private String description;
            private int id;
            private String image;
            private String name;
            private String no;
            private int status;

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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
