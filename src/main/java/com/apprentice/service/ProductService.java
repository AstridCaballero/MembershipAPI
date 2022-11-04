package com.apprentice.service;

import com.apprentice.models.Product;
import com.apprentice.repositories.ProductRepository;

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
public class ProductService {
    @Inject
    ProductRepository productRepository;


    /**
     * Stores Product information
     */
    public void storeProduct(final Product product) {
        productRepository.persist(product);
    }

    /**
     * Returns all the Products in a List
     */
    public List<Product> findAll() {
        return productRepository.listAll();
    }

    /**
     * Returns one Product by Id
     */
    public Product findByProductId(final Long productId) {
        return productRepository.findById(productId);
    }
}
