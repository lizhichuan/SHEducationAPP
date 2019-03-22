package com.shaohua.sheducation.resultbean;

import java.util.List;

/**
 * Created by chuan on 2017/11/28.
 */

public class GetUserInfoVO {

    /**
     * message : string
     * result : {"address":"string","age":0,"avatar":"string","badges":[{"description":"string","id":0,"image":"string","name":"string","no":"string","status":0}],"education":0,"friendStatus":0,"gender":0,"id":0,"labels":[{"abbr":"string","id":0,"image":"string","name":"string","no":"string","parent":"string","status":0,"type":0}],"nickName":"string"}
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
         * address : string
         * age : 0
         * avatar : string
         * badges : [{"description":"string","id":0,"image":"string","name":"string","no":"string","status":0}]
         * education : 0
         * friendStatus : 0
         * gender : 0
         * id : 0
         * labels : [{"abbr":"string","id":0,"image":"string","name":"string","no":"string","parent":"string","status":0,"type":0}]
         * nickName : string
         */

        private String address;
        private int age;
        private String avatar;
        private int education;
        private int friendStatus;
        private int gender;
        private int id;
        private String nickName;
        private List<BadgesBean> badges;
        private List<LabelsBean> labels;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getEducation() {
            return education;
        }

        public void setEducation(int education) {
            this.education = education;
        }

        public int getFriendStatus() {
            return friendStatus;
        }

        public void setFriendStatus(int friendStatus) {
            this.friendStatus = friendStatus;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public List<BadgesBean> getBadges() {
            return badges;
        }

        public void setBadges(List<BadgesBean> badges) {
            this.badges = badges;
        }

        public List<LabelsBean> getLabels() {
            return labels;
        }

        public void setLabels(List<LabelsBean> labels) {
            this.labels = labels;
        }

        public static class BadgesBean {
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

        public static class LabelsBean {
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
    }
}
