package com.apprentice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * This entity contains the attributes that represent the fields of a table 'OrderEmployee' of a DB
 * it is using Object Relational Mapping - ORM in order to take an object 'OrderEmployee'
 * and map it into the table 'OrderEmployee' of a database
 */
@Entity
@Data //Lombok takes care of the getters and setters
@AllArgsConstructor //Lombok creates a constructor with all the attributes as args
@NoArgsConstructor //Lombok creates a default empty constructor
@ToString //Lombok overrides the .toString()
public class OrderEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //to create the id automatically
//    @JsonIgnore
    private Long orderEmployeeId;

    @ManyToOne(targetEntity = Card.class, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "cardId")
    private Card card;

    @OneToMany(targetEntity = OrderProducts.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
//    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private List<OrderProducts> orderProductsList;

    private double orderTotal = 0.0;

    public void setOrderTotal(double orderTotal) {
        this.orderTotal += orderTotal;
    }
}
