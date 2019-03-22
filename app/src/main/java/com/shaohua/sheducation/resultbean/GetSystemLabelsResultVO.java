package com.shaohua.sheducation.resultbean;

import java.util.List;

/**
 * Created by chuan on 2017/11/24.
 */

public class GetSystemLabelsResultVO {

    /**
     * message : string
     * result : [{"abbr":"string","id":0,"image":"string","name":"string","no":"string","parent":"string","type":0}]
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

    public static class ResultBean {
        /**
         * abbr : string
         * id : 0
         * image : string
         * name : string
         * no : string
         * parent : string
         * type : 0
         */

        private String abbr;
        private int id;
        private String image;
        private String name;
        private String no;
        private String parent;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
