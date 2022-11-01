package com.apprentice.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Employee {

    @Id
    private String employeeId;

    @OneToOne(targetEntity = Card.class)
    @JoinColumn(name = "cardId")
    private String cardId;
//
    private String employeeName;
    private String employeeEmail;
}
