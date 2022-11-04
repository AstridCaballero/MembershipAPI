package com.apprentice.service;

import com.apprentice.models.OrderProducts;
import com.apprentice.models.Product;
import com.apprentice.repositories.OrderProductRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementing business logic
 */

//@ApplicationScoped allows Quarkus to recognise this interface and inject it when called
@ApplicationScoped
@Transactional
public class OrderProductService {
    @Inject
    OrderProductRepository orderProductRepository;

    @Inject
    ProductService productService;

    /**
     * Stores OrderProducts information
     */
    public Long storeOrderProduct(final OrderProducts orderProducts) {
        //find the product and its price per unit
        final Product product= productService.findByProductId(orderProducts.getProduct().getProductId());
        double priceOrderProducts = product.getProductPrice();
        //set the price to the orderProducts that will be created
        orderProducts.setOrderProductsPrice(priceOrderProducts);
        //persist the orderProduct
        orderProductRepository.persist(orderProducts);
        //get the generated Id
        orderProductRepository.flush();
        //return the orderProductsId
        return orderProducts.getOrderProductsId();
    }

    /**
     * Returns all the OrderProducts in a List that belong to the same OrderEmployee
     */
    public List<OrderProducts> findAllByOrderId(final Long orderId) {
        return orderProductRepository.list("orderId", orderId);
    }

    /**
     * Returns one OrderProduct by id
     */
    public OrderProducts findByOrderProductId(final Long orderProductsId) {
        return  orderProductRepository.findById(orderProductsId);
    }

    /**
     * Deletes OrderProducts
     */
    public void removeOrderProduct(final Long orderProductsId) {
        orderProductRepository.deleteById(orderProductsId);
    }

    /**
     * Updates quantity and price of the OrderProducts
     */
    public void updateOrderProduct(final Long orderProductsId) {
        //update quantity of the orderProduct
        OrderProducts orderProductsToUpdate = orderProductRepository.findById(orderProductsId);
        orderProductsToUpdate.setOrderProductsQuantity();
        final int quantity = orderProductsToUpdate.getOrderProductsQuantity();
        //Get price of product
        final Product product = productService.findByProductId(orderProductsToUpdate.getProduct().getProductId());
        final double newPriceOrderProducts = product.getProductPrice() * quantity;
        //update price
        orderProductsToUpdate.setOrderProductsPrice(newPriceOrderProducts);
    }
}
