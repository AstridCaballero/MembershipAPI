package com.apprentice.service;

import com.apprentice.models.TopUp;
import com.apprentice.repositories.TopUpRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Implementing business logic
 */

//@ApplicationScoped allows Quarkus to recognise this interface and inject it when called
@ApplicationScoped
@Transactional
public class TopUpService {
    @Inject
    TopUpRepository topUpRepository;

    /**
     * Stores TopUP information
     */
    public void registerTopUp(final TopUp topUp) {
        topUpRepository.persist(topUp);
    }
}
