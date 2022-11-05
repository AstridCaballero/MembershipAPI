package com.apprentice.service;

import com.apprentice.models.Card;
import com.apprentice.repositories.CardRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;

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

    /**
     * Updates card's balance
     */
    public void updateBalance(final String cardId, final double amount) {
        Card card = cardRepository.findByCardId(cardId);
        card.setCardBalance(amount);
    }

    /**
     * Returns boolean if balance is greater or equal to orderTotal
     *
     */
    public boolean isBalanceGreaterThanPayment(final String cardId, final double payment) {
        return cardRepository.findByCardId(cardId).getCardBalance() >= payment;
    }

    /**
     * Update lastInteractionDateTime to track inactivity
     */
    public void updateLastInteractionDateTime(final String cardId) {
        Card card = cardRepository.findByCardId(cardId);
        card.setLastInteractionDateTime(ZonedDateTime.now());
    }
}
