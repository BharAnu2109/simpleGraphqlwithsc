package com.banking.graphql.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String transactionId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private String currency;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    
    private LocalDateTime transactionDate;
    
    private String referenceNumber;
    
    private String beneficiaryName;
    
    private String beneficiaryAccount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;
    
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER, PAYMENT, FEE, INTEREST, REFUND
    }
    
    public enum TransactionStatus {
        PENDING, COMPLETED, FAILED, REVERSED, CANCELLED
    }
}
