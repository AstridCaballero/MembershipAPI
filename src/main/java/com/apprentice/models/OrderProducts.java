package com.apprentice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data //Lombok takes care of the getters and setters
@AllArgsConstructor //Lombok creates a constructor with all the attributes as args
@NoArgsConstructor //Lombok creates a default empty constructor
@ToString //Lombok overrides the .toString()
public class OrderProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //to create the id automatically
    private Long orderProductsId;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne(targetEntity = OrderEmployee.class)
    @JoinColumn(name = "orderId")
    private OrderEmployee orderEmployee;

    private int orderProductsQuantity = 1;
    private double orderProductsPrice;
}
