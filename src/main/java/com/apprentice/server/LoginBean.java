package com.apprentice.server;
public class LoginBean {

    public String cardId;
    public String pinNumber;

    public LoginBean() {
    }

    public LoginBean(String cardId, String pinNumber) {
        this.cardId = cardId;
        this.pinNumber = pinNumber;
    }
}
