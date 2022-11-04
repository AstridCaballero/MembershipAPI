package com.apprentice.repositories;

import com.apprentice.models.OrderProducts;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * this class contains the 'sql queries' that will be performed on the DB
 */

//@ApplicationScoped allows Quarkus to recognise this class and inject it when called
@ApplicationScoped
public class OrderProductRepository implements PanacheRepository<OrderProducts> {
}
