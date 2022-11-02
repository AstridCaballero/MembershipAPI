package com.apprentice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
public class Employee extends PanacheEntityBase {

    @Id
    private String employeeId;

    @OneToOne(targetEntity = Card.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cardId")
    @JsonIgnore
    private Card card;

    private String employeeName;
    private String employeeEmail;
    private String employeeMobileNumber;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeMobileNumber() {
        return employeeMobileNumber;
    }

    public void setEmployeeMobileNumber(String employeeMobileNumber) {
        this.employeeMobileNumber = employeeMobileNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "employeeId='" + employeeId + '\'' +
            ", card=" + card +
            ", employeeName='" + employeeName + '\'' +
            ", employeeEmail='" + employeeEmail + '\'' +
            ", employeeMobileNumber='" + employeeMobileNumber + '\'' +
            '}';
    }
}
