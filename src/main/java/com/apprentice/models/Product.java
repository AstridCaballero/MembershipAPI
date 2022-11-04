package com.apprentice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

/**
 * This entity contains the attributes that represent the fields of a table 'Product' of a DB
 * it is using Object Relational Mapping - ORM in order to take an object Product
 * and map it into the table 'Product' of a database
 */
@Entity
@Data //Lombok takes care of the getters and setters
@AllArgsConstructor //Lombok creates a constructor with all the attributes as args
@NoArgsConstructor //Lombok creates a default empty constructor
@ToString //Lombok overrides the .toString()
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //to create the id automatically
    private Long productId;

    private String productName;
    private double productPrice;

    @OneToMany(targetEntity = OrderProducts.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<OrderProducts> orderProductsList; //Got MultipleBagFetchException, so using 'Set' instead of 'List' solves the issue
}
