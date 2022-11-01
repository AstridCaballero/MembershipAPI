package com.apprentice.server;

import javax.enterprise.context.ApplicationScoped;

//@ApplicationScoped allows Quarkus to recognise this interface and inject it when called
@ApplicationScoped
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
