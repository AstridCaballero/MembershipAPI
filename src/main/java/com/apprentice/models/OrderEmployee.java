package com.apprentice.models;

import javax.persistence.*;

@Entity
public class OrderEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderEmployeeId;

    @ManyToOne(targetEntity = Card.class)
    @JoinColumn(name = "cardId")
    private String cardId;
}
