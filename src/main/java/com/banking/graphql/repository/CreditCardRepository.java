package com.banking.graphql.repository;

import com.banking.graphql.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    Optional<CreditCard> findByCardNumber(String cardNumber);
    List<CreditCard> findByCustomerId(Long customerId);
    List<CreditCard> findByCardType(CreditCard.CardType cardType);
    List<CreditCard> findByStatus(CreditCard.CardStatus status);
}
