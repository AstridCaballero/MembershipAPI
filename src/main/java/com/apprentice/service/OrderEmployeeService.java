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


    public OrderEmployee findById(Long orderEmployeeId) {
        return orderEmployeeRepository.findByIdOptional(orderEmployeeId).orElse(null);
    }
    public void updateOrderEmployee(final OrderEmployee orderEmployee) {
//        orderEmployeeRepository.update(
//            "update from OrderEmployee set orderTotal = ?, where orderEmployeeId = ?",
//            orderEmployee.getOrderTotal(), orderEmployee.getOrderEmployeeId());

        OrderEmployee orderEmployeeToUpdate = orderEmployeeRepository.findById(orderEmployee.getOrderEmployeeId());
        orderEmployeeToUpdate.setOrderTotal(orderEmployee.getOrderTotal());

    }
}
