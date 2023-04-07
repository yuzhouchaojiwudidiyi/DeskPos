package com.wellsun.deskpos.cardbean;

/**
 * date     : 2023-03-24
 * author   : ZhaoZheng
 * describe :
 */
public class Card0005 {
    String sendCode1 = "8696";  //发卡方代码 2字节
    String cityCode2 = "3301";  //城市代码   2字节
    String tradeCode3 = "0075";  //行业代码  2字节
    String fruCode4 = "FFFF";  //RFU  2字节
    String cardNumber5 = "0000000000000000";  //应用序列号  8字节
    String cardType6 = "0000";  //卡类型  2字节
    String date7 = "00000000";  //发卡日期  4字节
    String device8 = "000000000000";  //发卡设备  6字节
    String version9 = "0000";  //应用版本号  2字节

    public void subCard0005(String data) {
        sendCode1=data.substring(0,4);
        cityCode2=data.substring(4,8);
        tradeCode3=data.substring(8,12);
        fruCode4=data.substring(12,16);
        cardNumber5=data.substring(16,32);
        cardType6=data.substring(32,36);
        date7=data.substring(36,44);
        device8=data.substring(44,56);
        version9=data.substring(56,60);
    }

    public String getStr() {
        return sendCode1 + cityCode2 + tradeCode3 + fruCode4 + cardNumber5 + cardType6 + date7 + device8 + version9;
    }

    public String getSendCode1() {
        return sendCode1;
    }

    public void setSendCode1(String sendCode1) {
        this.sendCode1 = sendCode1;
    }

    public String getCityCode2() {
        return cityCode2;
    }

    public void setCityCode2(String cityCode2) {
        this.cityCode2 = cityCode2;
    }

    public String getTradeCode3() {
        return tradeCode3;
    }

    public void setTradeCode3(String tradeCode3) {
        this.tradeCode3 = tradeCode3;
    }

    public String getFruCode4() {
        return fruCode4;
    }

    public void setFruCode4(String fruCode4) {
        this.fruCode4 = fruCode4;
    }

    public String getCardNumber5() {
        return cardNumber5;
    }

    public void setCardNumber5(String cardNumber5) {
        this.cardNumber5 = cardNumber5;
    }

    public String getCardType6() {
        return cardType6;
    }

    public void setCardType6(String cardType6) {
        this.cardType6 = cardType6;
    }

    public String getDate7() {
        return date7;
    }

    public void setDate7(String date7) {
        this.date7 = date7;
    }

    public String getDevice8() {
        return device8;
    }

    public void setDevice8(String device8) {
        this.device8 = device8;
    }

    public String getVersion9() {
        return version9;
    }

    public void setVersion9(String version9) {
        this.version9 = version9;
    }


}
