package com.apprentice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
