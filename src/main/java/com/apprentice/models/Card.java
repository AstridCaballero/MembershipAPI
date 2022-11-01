package com.apprentice.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * This entity contains the attributes that represent the fields of a table 'card' of a DB
 * it is usign Object Relational Mapping - ORM in order to take an object Card
 * and map it into the table 'card' of a database
 */
@Entity
//@Table(name = "cardEmployee")
public class Card extends PanacheEntityBase {
    @Id
    private String cardId;

    @OneToOne(targetEntity = Employee.class, mappedBy = "card")
    private Employee employee;
    private int cardPassCode;
    private double cardBalance;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getCardPassCode() {
        return cardPassCode;
    }

    public void setCardPassCode(int cardPassCode) {
        this.cardPassCode = cardPassCode;
    }

    public double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(double cardBalance) {
        this.cardBalance = cardBalance;
    }

    @Override
    public String toString() {
        return "Card{" +
            "cardId='" + cardId + '\'' +
            ", employee=" + employee +
            ", cardPassCode=" + cardPassCode +
            ", cardBalance=" + cardBalance +
            '}';
    }
}
