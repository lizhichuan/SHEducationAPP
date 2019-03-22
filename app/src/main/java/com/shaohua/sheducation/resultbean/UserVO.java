package com.shaohua.sheducation.resultbean;

import java.util.List;

/**
 * Created by chuan on 2017/11/16.
 */

public class UserVO {


    /**
     * address : string
     * age : 0
     * appPush : 0
     * avatar : string
     * badges : [{"description":"string","id":0,"image":"string","name":"string","no":"string","status":0}]
     * education : 0
     * friendPush : 0
     * friendStatus : 0
     * gender : 0
     * id : 0
     * labels : [{"abbr":"string","id":0,"image":"string","name":"string","no":"string","parent":"string","status":0,"type":0}]
     * messageNum : 0
     * mobile : string
     * nickName : string
     * privacyOptions : 0
     */

    private String address;
    private int age;
    private int appPush;
    private String avatar;
    private int education;
    private int friendPush;
    private int friendStatus;
    private int gender;
    private int id;
    private int messageNum;
    private String mobile;
    private String nickName;
    private int privacyOptions;
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

    public int getAppPush() {
        return appPush;
    }

    public void setAppPush(int appPush) {
        this.appPush = appPush;
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

    public int getFriendPush() {
        return friendPush;
    }

    public void setFriendPush(int friendPush) {
        this.friendPush = friendPush;
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

    public int getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPrivacyOptions() {
        return privacyOptions;
    }

    public void setPrivacyOptions(int privacyOptions) {
        this.privacyOptions = privacyOptions;
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
