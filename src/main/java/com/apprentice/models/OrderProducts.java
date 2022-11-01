package com.apprentice.models;

import javax.persistence.*;

@Entity
public class OrderProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductsId;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "productId")
    private Long productId;

    @ManyToOne(targetEntity = OrderEmployee.class)
    @JoinColumn(name = "orderId")
    private Long orderId;

    private int orderProductsQuantity;
    private double orderProductsPrice;
}
