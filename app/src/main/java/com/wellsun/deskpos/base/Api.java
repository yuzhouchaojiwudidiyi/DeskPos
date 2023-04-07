package com.wellsun.deskpos.base;

/**
 * date     : 2023-03-21
 * author   : ZhaoZheng
 * describe :
 */
public interface Api {
    String host = "http://192.168.1.195:9998";
    String cardReset = host + "/cardInit/cardReset";
    String getCardNumber = host + "/cardInit/getCardNumber";
    String openCard = host + "/cardData/init";
    String getCardMessage = host + "/cardData/getUserMesage";
    String rechargeSuccess = host + "/cardData/recharge";
}
