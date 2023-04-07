package com.wellsun.deskpos.cardbean;

/**
 * date     : 2023-03-24
 * author   : ZhaoZheng
 * describe :
 */
public class Card0016 {
    String userType1 = "00";  //持卡人类别标识 1字节
    String userTag2  = "00";  //持卡人职工标识   1字节
    String userName3 = "3030303030303030303030303030303030303030";  //持卡人姓名  20字节
    String userId4 = "3030303030303030303030303030303030303030303030303030303030303030";  //持卡人证件号码  32字节
    String userIdType5 = "00";   //持卡人证件类型 1字节


    public void subCard0016(String data) {
        userType1 = data.substring(0,2);
        userTag2 = data.substring(2, 4);
        userName3 = data.substring(4, 44);
        userId4 = data.substring(44, 108);
        userIdType5 = data.substring(108, 110);
    }


    public String getStr() {
        return userType1 + userTag2 + userName3 + userId4 + userIdType5;
    }

    public String getUserType1() {
        return userType1;
    }

    public void setUserType1(String userType1) {
        this.userType1 = userType1;
    }

    public String getUserTag2() {
        return userTag2;
    }

    public void setUserTag2(String userTag2) {
        this.userTag2 = userTag2;
    }

    public String getUserName3() {
        return userName3;
    }

    public void setUserName3(String userName3) {
        this.userName3 = userName3;
    }

    public String getUserId4() {
        return userId4;
    }

    public void setUserId4(String userId4) {
        this.userId4 = userId4;
    }

    public String getUserIdType5() {
        return userIdType5;
    }

    public void setUserIdType5(String userIdType5) {
        this.userIdType5 = userIdType5;
    }

}
