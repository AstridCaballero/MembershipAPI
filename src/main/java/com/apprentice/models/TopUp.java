package com.apprentice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * This entity contains the attributes that represent the fields of a table 'TopUp' of a DB
 * it is using Object Relational Mapping - ORM in order to take an object TopUp
 * and map it into the table 'TopUp' of a database
 */
@Entity
@Data //Lombok takes care of the getters and setters
@AllArgsConstructor //Lombok creates a constructor with all the attributes as args
@NoArgsConstructor //Lombok creates a default empty constructor
@ToString //Lombok overrides the .toString()
public class TopUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //to create the id automatically
    @JsonIgnore
    private Long topUpId;

    //   The below attribute @ManyToOne relationship doesn't include CascadeType.PERSIST
    //   Because when a TopUp object is created (persisted) we don't want to persist a Card Object
    //   as this already exists
    @ManyToOne(targetEntity = Card.class, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "cardId")
    private Card card;

    private double topUpAmount;

    //  When registering a card, CardBalance should be set to zero
    //  later 'topUp' will update the balance
    //  @JsonIgnore will remove the field from the json body request
    //  as cardBalance value is set by default to zero
    @JsonIgnore
    private ZonedDateTime topUpDateTime = ZonedDateTime.now();
}
