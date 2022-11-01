package com.apprentice.models;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
public class TopUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topUpId;

    @ManyToOne(targetEntity = Card.class)
    @JoinColumn(name = "cardId")
    private String cardId;
    private double topUpAmount;
    private ZonedDateTime topUpDateTime;
}
