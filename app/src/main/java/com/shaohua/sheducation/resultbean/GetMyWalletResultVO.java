package com.shaohua.sheducation.resultbean;

/**
 * Created by chuan on 2017/11/24.
 */

public class GetMyWalletResultVO {

    /**
     * message : string
     * result : {"balance":0,"coin":0}
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
         * balance : 0
         * coin : 0
         */

        private int balance;
        private int coin;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }
    }
}
