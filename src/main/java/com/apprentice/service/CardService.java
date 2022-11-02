package com.apprentice.service;

import com.apprentice.models.Card;
import com.apprentice.repositories.CardRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Implementing business logic
 */

//@ApplicationScoped allows Quarkus to recognise this interface and inject it when called
@ApplicationScoped
@Transactional
public class CardService {

    @Inject
    CardRepository cardRepository;

    /**
     * Stores Card information
     */

    public void registerCard(final Card card) {
        cardRepository.persist(card);
    }

    /**
     * Returns Card information by cardId
     */

    public Card findCard(final String cardId) {
        return cardRepository.findByCardId(cardId);
    }

    public void updateBalance(final String cardId, final double cardBalance) {
        Card card = cardRepository.findByCardId(cardId);
        card.setCardBalance(cardBalance);
    }
}
