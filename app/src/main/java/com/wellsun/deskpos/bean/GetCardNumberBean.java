package com.wellsun.deskpos.bean;

/**
 * date     : 2023-03-24
 * author   : ZhaoZheng
 * describe :
 */
public class GetCardNumberBean {

    /**
     * msg : 操作成功
     * code : 0
     * data : {"cardNumber":"2140000001504501","cardType":null,"storageTime":null,"endoce":null,"repeatFlag":null,"cardCsn":"FD7534B1","iniFlag":"0"}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cardNumber : 2140000001504501
         * cardType : null
         * storageTime : null
         * endoce : null
         * repeatFlag : null
         * cardCsn : FD7534B1
         * iniFlag : 0
         */

        private String cardNumber;
        private Object cardType;
        private Object storageTime;
        private Object endoce;
        private Object repeatFlag;
        private String cardCsn;
        private String iniFlag;

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public Object getCardType() {
            return cardType;
        }

        public void setCardType(Object cardType) {
            this.cardType = cardType;
        }

        public Object getStorageTime() {
            return storageTime;
        }

        public void setStorageTime(Object storageTime) {
            this.storageTime = storageTime;
        }

        public Object getEndoce() {
            return endoce;
        }

        public void setEndoce(Object endoce) {
            this.endoce = endoce;
        }

        public Object getRepeatFlag() {
            return repeatFlag;
        }

        public void setRepeatFlag(Object repeatFlag) {
            this.repeatFlag = repeatFlag;
        }

        public String getCardCsn() {
            return cardCsn;
        }

        public void setCardCsn(String cardCsn) {
            this.cardCsn = cardCsn;
        }

        public String getIniFlag() {
            return iniFlag;
        }

        public void setIniFlag(String iniFlag) {
            this.iniFlag = iniFlag;
        }
    }
}
