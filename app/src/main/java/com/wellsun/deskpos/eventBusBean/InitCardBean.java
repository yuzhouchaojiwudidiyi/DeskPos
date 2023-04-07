package com.wellsun.deskpos.eventBusBean;

/**
 * date     : 2023-03-27
 * author   : ZhaoZheng
 * describe :
 */
public class InitCardBean {
    String cardId;
    String cardState;
    String cardDate;
    String cardType;
    String cardBalance;
    String cardFree;
    String userId;
    String userName;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardState() {
        return cardState;
    }

    public void setCardState(String cardState) {
        this.cardState = cardState;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }

    public String getCardFree() {
        return cardFree;
    }

    public void setCardFree(String cardFree) {
        this.cardFree = cardFree;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
