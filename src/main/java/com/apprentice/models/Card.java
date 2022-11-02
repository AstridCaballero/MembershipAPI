package com.apprentice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * This entity contains the attributes that represent the fields of a table 'card' of a DB
 * it is usign Object Relational Mapping - ORM in order to take an object Card
 * and map it into the table 'card' of a database
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@JsonIgnoreProperties({"HibernateLazyInitializer", "Handler"})
public class Card extends PanacheEntityBase {
    @Id
    private String cardId;

    @OneToOne(targetEntity = Employee.class, mappedBy = "card", fetch = FetchType.LAZY)
    private Employee employee;
    private int cardPassCode;

    //  When registering a card, CardBalance should be set to zero
    //  later 'topUp' will update the balance
    //  @JsonIgnore will remove the field from the json body request
    //  as cardBalance value is set by default to zero
    @JsonIgnore
    private double cardBalance = 0.0;

    @OneToMany(targetEntity = TopUp.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
        mappedBy = "card")
    @JsonIgnore
    private List<TopUp> topUp;
}
