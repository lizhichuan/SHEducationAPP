package com.shaohua.sheducation.resultbean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chuan on 2017/11/24.
 */

public class GetMyMessageResultVO implements Serializable{

    /**
     * message : string
     * result : {"count":0,"data":[{"content":"string","createTime":0,"id":0,"relationId":0,"status":0,"title":"string","type":"string"}],"page":0,"size":0}
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

    public static class ResultBean implements Serializable{
        /**
         * count : 0
         * data : [{"content":"string","createTime":0,"id":0,"relationId":0,"status":0,"title":"string","type":"string"}]
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

        public static class DataBean implements Serializable {
            /**
             * content : string
             * createTime : 0
             * id : 0
             * relationId : 0
             * status : 0
             * title : string
             * type : string
             */

            private String content;
            private int createTime;
            private int id;
            private int relationId;
            private int status;
            private String title;
            private String type;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCreateTime() {
                return createTime;
            }

            public void setCreateTime(int createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getRelationId() {
                return relationId;
            }

            public void setRelationId(int relationId) {
                this.relationId = relationId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
