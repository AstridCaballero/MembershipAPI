package com.apprentice.repositories;

import com.apprentice.models.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

//@ApplicationScoped allows Quarkus to recognise this class and inject it when called
@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
}
