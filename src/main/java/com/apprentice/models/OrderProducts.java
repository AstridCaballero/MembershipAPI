package com.apprentice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private int orderProductsQuantity = 1;
    private double orderProductsPrice;

    /**
     * product quantity goes up one unit at the time
     */
    public void setOrderProductsQuantity() {
        this.orderProductsQuantity += 1;
    }
}
