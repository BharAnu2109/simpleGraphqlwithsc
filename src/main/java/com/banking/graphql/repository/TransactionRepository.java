package com.banking.graphql.repository;

import com.banking.graphql.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionId(String transactionId);
    List<Transaction> findByAccountId(Long accountId);
    List<Transaction> findByType(Transaction.TransactionType type);
    List<Transaction> findByStatus(Transaction.TransactionStatus status);
}
