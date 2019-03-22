package com.shaohua.sheducation.resultbean;

import java.util.List;

/**
 * Created by chuan on 2017/11/24.
 */

public class GetBannersResultVO {


    /**
     * message : string
     * result : [{"content":"string","image":"string","originImage":"string","otherLink":"string","title":"string"}]
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
         * content : string
         * image : string
         * originImage : string
         * otherLink : string
         * title : string
         */

        private String content;
        private String image;
        private String originImage;
        private String otherLink;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getOriginImage() {
            return originImage;
        }

        public void setOriginImage(String originImage) {
            this.originImage = originImage;
        }

        public String getOtherLink() {
            return otherLink;
        }

        public void setOtherLink(String otherLink) {
            this.otherLink = otherLink;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
