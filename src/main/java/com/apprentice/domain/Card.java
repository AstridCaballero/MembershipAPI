package com.apprentice.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Card {

    @Id
    public String cardId;
    public int cardPassCode;
    public double cardBalance;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
