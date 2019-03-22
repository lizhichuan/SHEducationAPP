package com.shaohua.sheducation.resultbean;

import java.util.List;

/**
 * Created by chuan on 2017/11/24.
 */

public class GetPlanRecommendResultVO {

    /**
     * message : string
     * result : {"count":0,"data":[{"completePercent":0,"createTime":0,"depositMode":0,"description":"string","id":0,"image":"string","label":{"abbr":"string","id":0,"image":"string","name":"string","no":"string","parent":"string","status":0,"type":0},"labelId":0,"members":[{"addressId":0,"age":0,"allowMsgPush":0,"avatar":"string","education":"string","gender":0,"id":0,"mobile":"string","nickName":"string","privacyOptions":0,"taobaoOpenid":"string","userName":"string","wechatOpenid":"string"}],"name":"string","planMode":0,"status":0,"testDest":"string","testTime":0,"userId":0}],"page":0,"size":0}
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
         * data : [{"completePercent":0,"createTime":0,"depositMode":0,"description":"string","id":0,"image":"string","label":{"abbr":"string","id":0,"image":"string","name":"string","no":"string","parent":"string","status":0,"type":0},"labelId":0,"members":[{"addressId":0,"age":0,"allowMsgPush":0,"avatar":"string","education":"string","gender":0,"id":0,"mobile":"string","nickName":"string","privacyOptions":0,"taobaoOpenid":"string","userName":"string","wechatOpenid":"string"}],"name":"string","planMode":0,"status":0,"testDest":"string","testTime":0,"userId":0}]
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
             * members : [{"addressId":0,"age":0,"allowMsgPush":0,"avatar":"string","education":"string","gender":0,"id":0,"mobile":"string","nickName":"string","privacyOptions":0,"taobaoOpenid":"string","userName":"string","wechatOpenid":"string"}]
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
                 * addressId : 0
                 * age : 0
                 * allowMsgPush : 0
                 * avatar : string
                 * education : string
                 * gender : 0
                 * id : 0
                 * mobile : string
                 * nickName : string
                 * privacyOptions : 0
                 * taobaoOpenid : string
                 * userName : string
                 * wechatOpenid : string
                 */

                private int addressId;
                private int age;
                private int allowMsgPush;
                private String avatar;
                private String education;
                private int gender;
                private int id;
                private String mobile;
                private String nickName;
                private int privacyOptions;
                private String taobaoOpenid;
                private String userName;
                private String wechatOpenid;

                public int getAddressId() {
                    return addressId;
                }

                public void setAddressId(int addressId) {
                    this.addressId = addressId;
                }

                public int getAge() {
                    return age;
                }

                public void setAge(int age) {
                    this.age = age;
                }

                public int getAllowMsgPush() {
                    return allowMsgPush;
                }

                public void setAllowMsgPush(int allowMsgPush) {
                    this.allowMsgPush = allowMsgPush;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getEducation() {
                    return education;
                }

                public void setEducation(String education) {
                    this.education = education;
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

                public String getTaobaoOpenid() {
                    return taobaoOpenid;
                }

                public void setTaobaoOpenid(String taobaoOpenid) {
                    this.taobaoOpenid = taobaoOpenid;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getWechatOpenid() {
                    return wechatOpenid;
                }

                public void setWechatOpenid(String wechatOpenid) {
                    this.wechatOpenid = wechatOpenid;
                }
            }
        }
    }
}
