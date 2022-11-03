package com.apprentice.repositories;

import com.apprentice.models.TopUp;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * this class contains the 'sql queries' that will be performed on the DB
 */

//@ApplicationScoped allows Quarkus to recognise this class and inject it when called
@ApplicationScoped
public class TopUpRepository implements PanacheRepository<TopUp> {
    public List<TopUp> findAllByCardId(final int cardId) {
        return find("cardId", cardId).stream().toList();
    }
}
