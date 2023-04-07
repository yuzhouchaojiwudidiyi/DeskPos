package com.wellsun.deskpos.cardbean;

/**
 * date     : 2023-03-24
 * author   : ZhaoZheng
 * describe :
 */
public class Card0015 {
    String sendCode1 = "8696";  //发卡方代码 2字节
    String cityCode2 = "3301";  //城市代码   2字节
    String tradeCode3 = "0075";  //行业代码  2字节
    String fruCode4 = "FF";  //RFU  1字节
    String cardFee5 = "00";   //押金 1字节
    String cardState6 = "00";   //卡状态 1字节  00未启用 01已启用
    String cardVersion7 = "00";   //版本 1字节
    String cityLink8 = "0000";   //参与城市互通标识 城市代码 2字节
    String cardNumber9 = "0000000000000000";  //应用序列号  8字节
    String startDate10 = "00000000";  //启用日期  4字节
    String endDate11 = "00000000";  //有效日期  4字节
    String cardTypeFirst12 = "00";  //发卡设备  1字节
    String versionSecond13 = "00";  //应用版本号  1字节

    public void subCard0015(String data) {
        sendCode1 = data.substring(0, 4);
        cityCode2 = data.substring(4, 8);
        tradeCode3 = data.substring(8, 12);
        fruCode4 = data.substring(12, 14);
        cardFee5 = data.substring(14, 16);
        cardState6 = data.substring(16, 18);
        cardVersion7 = data.substring(18, 20);
        cityLink8 = data.substring(20, 24);
        cardNumber9 = data.substring(24, 40);
        startDate10 = data.substring(40, 48);
        endDate11 = data.substring(48, 56);
        cardTypeFirst12 = data.substring(56, 58);
        versionSecond13 = data.substring(58, 60);
    }


    public String getStr() {
        return sendCode1 + cityCode2 + tradeCode3 + fruCode4 + cardFee5 + cardState6 + cardVersion7 + cityLink8
                + cardNumber9 + startDate10 + endDate11 + cardTypeFirst12 + versionSecond13;
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

    public String getCardFee5() {
        return cardFee5;
    }

    public void setCardFee5(String cardFee5) {
        this.cardFee5 = cardFee5;
    }

    public String getCardState6() {
        return cardState6;
    }

    public void setCardState6(String cardState6) {
        this.cardState6 = cardState6;
    }

    public String getCardVersion7() {
        return cardVersion7;
    }

    public void setCardVersion7(String cardVersion7) {
        this.cardVersion7 = cardVersion7;
    }

    public String getCityLink8() {
        return cityLink8;
    }

    public void setCityLink8(String cityLink8) {
        this.cityLink8 = cityLink8;
    }

    public String getCardNumber9() {
        return cardNumber9;
    }

    public void setCardNumber9(String cardNumber9) {
        this.cardNumber9 = cardNumber9;
    }

    public String getStartDate10() {
        return startDate10;
    }

    public void setStartDate10(String startDate10) {
        this.startDate10 = startDate10;
    }

    public String getEndDate11() {
        return endDate11;
    }

    public void setEndDate11(String endDate11) {
        this.endDate11 = endDate11;
    }

    public String getCardTypeFirst12() {
        return cardTypeFirst12;
    }

    public void setCardTypeFirst12(String cardTypeFirst12) {
        this.cardTypeFirst12 = cardTypeFirst12;
    }

    public String getVersionSecond13() {
        return versionSecond13;
    }

    public void setVersionSecond13(String versionSecond13) {
        this.versionSecond13 = versionSecond13;
    }


}
