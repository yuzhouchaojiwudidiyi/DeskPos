package com.wellsun.deskpos.bean;

/**
 * date     : 2023-03-30
 * author   : ZhaoZheng
 * describe :
 */
public class CardUserMessage {
    /**
     * msg : 操作成功
     * code : 0
     * data : {"cardNumber":"2140000001504501","cardType":"80","sendCardDate":"20230330100217","normalValidDate":"20250330","monthValidDate":"0","deposit":"50","swapNum":"0","psamNumber":"0","idNumber":"411322","txnCounter":0,"ptCounter":0,"ypCounter":0,"czCounter":0,"ljczje":0,"ljxfje":0,"ljxfcs":0,"cardBalance":0,"outofAccount":0,"cardCategory":0,"monthStartDatetime":"0","monthEndDatetime":"0","monthType":"0","monthtTime":"0","monthSwitchDatetime":"0","monthSwitchDatetype":"0","monthBalanceTime":"0","name":"zz","sex":"1","phoneNum":"185","memo":null,"encode":"0","lastTradingTime":"0","cityCardNumber":null,"address":"hz","birthday":null,"operateId":null,"deviceId":null}
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
         * cardType : 80
         * sendCardDate : 20230330100217
         * normalValidDate : 20250330
         * monthValidDate : 0
         * deposit : 50
         * swapNum : 0
         * psamNumber : 0
         * idNumber : 411322
         * txnCounter : 0
         * ptCounter : 0
         * ypCounter : 0
         * czCounter : 0
         * ljczje : 0
         * ljxfje : 0
         * ljxfcs : 0
         * cardBalance : 0
         * outofAccount : 0
         * cardCategory : 0
         * monthStartDatetime : 0
         * monthEndDatetime : 0
         * monthType : 0
         * monthtTime : 0
         * monthSwitchDatetime : 0
         * monthSwitchDatetype : 0
         * monthBalanceTime : 0
         * name : zz
         * sex : 1
         * phoneNum : 185
         * memo : null
         * encode : 0
         * lastTradingTime : 0
         * cityCardNumber : null
         * address : hz
         * birthday : null
         * operateId : null
         * deviceId : null
         */

        private String cardNumber;
        private String cardType;
        private String sendCardDate;
        private String normalValidDate;
        private String monthValidDate;
        private String deposit;
        private String swapNum;
        private String psamNumber;
        private String idNumber;
        private int txnCounter;
        private int ptCounter;
        private int ypCounter;
        private int czCounter;
        private int ljczje;
        private int ljxfje;
        private int ljxfcs;
        private int cardBalance;
        private int outofAccount;
        private int cardCategory;
        private String monthStartDatetime;
        private String monthEndDatetime;
        private String monthType;
        private String monthtTime;
        private String monthSwitchDatetime;
        private String monthSwitchDatetype;
        private String monthBalanceTime;
        private String name;
        private String sex;
        private String phoneNum;
        private Object memo;
        private String encode;
        private String lastTradingTime;
        private Object cityCardNumber;
        private String address;
        private String birthday;
        private Object operateId;
        private Object deviceId;

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getSendCardDate() {
            return sendCardDate;
        }

        public void setSendCardDate(String sendCardDate) {
            this.sendCardDate = sendCardDate;
        }

        public String getNormalValidDate() {
            return normalValidDate;
        }

        public void setNormalValidDate(String normalValidDate) {
            this.normalValidDate = normalValidDate;
        }

        public String getMonthValidDate() {
            return monthValidDate;
        }

        public void setMonthValidDate(String monthValidDate) {
            this.monthValidDate = monthValidDate;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public String getSwapNum() {
            return swapNum;
        }

        public void setSwapNum(String swapNum) {
            this.swapNum = swapNum;
        }

        public String getPsamNumber() {
            return psamNumber;
        }

        public void setPsamNumber(String psamNumber) {
            this.psamNumber = psamNumber;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public int getTxnCounter() {
            return txnCounter;
        }

        public void setTxnCounter(int txnCounter) {
            this.txnCounter = txnCounter;
        }

        public int getPtCounter() {
            return ptCounter;
        }

        public void setPtCounter(int ptCounter) {
            this.ptCounter = ptCounter;
        }

        public int getYpCounter() {
            return ypCounter;
        }

        public void setYpCounter(int ypCounter) {
            this.ypCounter = ypCounter;
        }

        public int getCzCounter() {
            return czCounter;
        }

        public void setCzCounter(int czCounter) {
            this.czCounter = czCounter;
        }

        public int getLjczje() {
            return ljczje;
        }

        public void setLjczje(int ljczje) {
            this.ljczje = ljczje;
        }

        public int getLjxfje() {
            return ljxfje;
        }

        public void setLjxfje(int ljxfje) {
            this.ljxfje = ljxfje;
        }

        public int getLjxfcs() {
            return ljxfcs;
        }

        public void setLjxfcs(int ljxfcs) {
            this.ljxfcs = ljxfcs;
        }

        public int getCardBalance() {
            return cardBalance;
        }

        public void setCardBalance(int cardBalance) {
            this.cardBalance = cardBalance;
        }

        public int getOutofAccount() {
            return outofAccount;
        }

        public void setOutofAccount(int outofAccount) {
            this.outofAccount = outofAccount;
        }

        public int getCardCategory() {
            return cardCategory;
        }

        public void setCardCategory(int cardCategory) {
            this.cardCategory = cardCategory;
        }

        public String getMonthStartDatetime() {
            return monthStartDatetime;
        }

        public void setMonthStartDatetime(String monthStartDatetime) {
            this.monthStartDatetime = monthStartDatetime;
        }

        public String getMonthEndDatetime() {
            return monthEndDatetime;
        }

        public void setMonthEndDatetime(String monthEndDatetime) {
            this.monthEndDatetime = monthEndDatetime;
        }

        public String getMonthType() {
            return monthType;
        }

        public void setMonthType(String monthType) {
            this.monthType = monthType;
        }

        public String getMonthtTime() {
            return monthtTime;
        }

        public void setMonthtTime(String monthtTime) {
            this.monthtTime = monthtTime;
        }

        public String getMonthSwitchDatetime() {
            return monthSwitchDatetime;
        }

        public void setMonthSwitchDatetime(String monthSwitchDatetime) {
            this.monthSwitchDatetime = monthSwitchDatetime;
        }

        public String getMonthSwitchDatetype() {
            return monthSwitchDatetype;
        }

        public void setMonthSwitchDatetype(String monthSwitchDatetype) {
            this.monthSwitchDatetype = monthSwitchDatetype;
        }

        public String getMonthBalanceTime() {
            return monthBalanceTime;
        }

        public void setMonthBalanceTime(String monthBalanceTime) {
            this.monthBalanceTime = monthBalanceTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public Object getMemo() {
            return memo;
        }

        public void setMemo(Object memo) {
            this.memo = memo;
        }

        public String getEncode() {
            return encode;
        }

        public void setEncode(String encode) {
            this.encode = encode;
        }

        public String getLastTradingTime() {
            return lastTradingTime;
        }

        public void setLastTradingTime(String lastTradingTime) {
            this.lastTradingTime = lastTradingTime;
        }

        public Object getCityCardNumber() {
            return cityCardNumber;
        }

        public void setCityCardNumber(Object cityCardNumber) {
            this.cityCardNumber = cityCardNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public Object getOperateId() {
            return operateId;
        }

        public void setOperateId(Object operateId) {
            this.operateId = operateId;
        }

        public Object getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Object deviceId) {
            this.deviceId = deviceId;
        }
    }
}
