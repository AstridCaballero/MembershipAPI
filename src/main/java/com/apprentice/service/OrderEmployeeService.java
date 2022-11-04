package com.apprentice.service;

import com.apprentice.models.OrderEmployee;
import com.apprentice.repositories.OrderEmployeeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Implementing business logic
 */

//@ApplicationScoped allows Quarkus to recognise this interface and inject it when called
@ApplicationScoped
@Transactional
public class OrderEmployeeService {
    @Inject
    OrderEmployeeRepository orderEmployeeRepository;

    /**
     * Create empty Order
     */
    public void createEmptyOrder(final OrderEmployee orderEmployee) {
        orderEmployeeRepository.persist(orderEmployee);
    }

    /**
     * Returns OrderEmployee by id
     */
    public OrderEmployee findById(Long orderEmployeeId) {
        return orderEmployeeRepository.findById(orderEmployeeId);
    }

    /**
     * Updates the total price ('orderTotal') to pay in an order ('OrderEmployee')
     * @param orderEmployee
     * @param orderProductsPrice
     */
    public void updateOrderEmployee(final OrderEmployee orderEmployee, final double orderProductsPrice) {
        OrderEmployee orderEmployeeToUpdate = orderEmployeeRepository.findById(orderEmployee.getOrderEmployeeId());
        orderEmployeeToUpdate.setOrderTotal(orderProductsPrice);
    }
}
