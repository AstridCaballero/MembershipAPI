package com.apprentice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * This entity contains the attributes that represent the fields of a table 'card' of a DB
 * it is using Object Relational Mapping - ORM in order to take an object Card
 * and map it into the table 'card' of a database
 */
@Entity
@Data //Lombok takes care of the getters and setters
@EqualsAndHashCode(callSuper = true) //Lombok takes care of the equals and hash methods
@AllArgsConstructor //Lombok creates a constructor with all the attributes as args
@NoArgsConstructor //Lombok creates a default empty constructor
@ToString //Lombok overrides the .toString()
public class Card extends PanacheEntityBase {
    @Id
    private String cardId;

    // @OneToOne associations are by default 'Eager',
    // meaning it will fetch a record early incurring
    // in bad performance because it will fetch the association
    // issuing unnecessary query 'selects' for every association
    // even if the association is not initialised. This is called N+1 query problems
    // I tried using a LAZY association but that lead to "LazyInitializationException"
    // The solution is to use @Fetch(FetchMode.JOIN), this tells hibernate that when calling
    // Card to issue an INNER JOIN with Employee so that information can be accessed too.
    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private Employee employee;

    private int cardPassCode;

    //  When registering a card, CardBalance should be set to zero
    //  later 'topUp' will update the balance
    //  @JsonIgnore will remove the field from the json body request
    //  as cardBalance value is set by default to zero
    @JsonIgnore
    private double cardBalance = 0.0;

    // Maps the bidirectional relationship between Card and TopUp
    // One Card can have zero or many top ups
    @OneToMany(targetEntity = TopUp.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private List<TopUp> topUpList;

    //Maps the bidirectional relationship between Card and OrderEmployee
    // One Card can have zero or many OrderEmployee
    @OneToMany(targetEntity = TopUp.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private List<OrderEmployee> orderEmployeeList;

    @JsonIgnore
    private ZonedDateTime lastInteractionDateTime;


    // This method allows for Card to persist employee
    public void setEmployee(Employee employee) {
        this.employee = employee;
        employee.setCard(this);
    }

    //setBalance is edited here as the amount passed as parameter
    // needs to make a cumulative sum/subtraction with the existing balance
    public void setCardBalance(double amount) {
        this.cardBalance += amount;
    }
}
