package com.shaohua.sheducation.resultbean;

import java.util.List;

/**
 * Created by chuan on 2017/11/25.
 */

public class GetStudySmartResultVO {

    /**
     * message : string
     * result : {"count":0,"data":[{"coins":0,"collectCount":0,"collectStatus":0,"file":"string","icon":"string","id":0,"image":"string","labelIds":"string","likeCount":0,"likeStatus":0,"link":"string","name":"string","no":"string","saleCount":0,"saleStatus":0}],"page":0,"size":0}
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
         * data : [{"coins":0,"collectCount":0,"collectStatus":0,"file":"string","icon":"string","id":0,"image":"string","labelIds":"string","likeCount":0,"likeStatus":0,"link":"string","name":"string","no":"string","saleCount":0,"saleStatus":0}]
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
             * coins : 0
             * collectCount : 0
             * collectStatus : 0
             * file : string
             * icon : string
             * id : 0
             * image : string
             * labelIds : string
             * likeCount : 0
             * likeStatus : 0
             * link : string
             * name : string
             * no : string
             * saleCount : 0
             * saleStatus : 0
             */

            private int coins;
            private int collectCount;
            private int collectStatus;
            private String file;
            private String icon;
            private int id;
            private String image;
            private String labelIds;
            private int likeCount;
            private int likeStatus;
            private String link;
            private String name;
            private String no;
            private int saleCount;
            private int saleStatus;

            public int getCoins() {
                return coins;
            }

            public void setCoins(int coins) {
                this.coins = coins;
            }

            public int getCollectCount() {
                return collectCount;
            }

            public void setCollectCount(int collectCount) {
                this.collectCount = collectCount;
            }

            public int getCollectStatus() {
                return collectStatus;
            }

            public void setCollectStatus(int collectStatus) {
                this.collectStatus = collectStatus;
            }

            public String getFile() {
                return file;
            }

            public void setFile(String file) {
                this.file = file;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
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

            public String getLabelIds() {
                return labelIds;
            }

            public void setLabelIds(String labelIds) {
                this.labelIds = labelIds;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }

            public int getLikeStatus() {
                return likeStatus;
            }

            public void setLikeStatus(int likeStatus) {
                this.likeStatus = likeStatus;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
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

            public int getSaleCount() {
                return saleCount;
            }

            public void setSaleCount(int saleCount) {
                this.saleCount = saleCount;
            }

            public int getSaleStatus() {
                return saleStatus;
            }

            public void setSaleStatus(int saleStatus) {
                this.saleStatus = saleStatus;
            }
        }
    }
}
