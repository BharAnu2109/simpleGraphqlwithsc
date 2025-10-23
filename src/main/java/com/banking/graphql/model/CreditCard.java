package com.banking.graphql.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_cards")
public class CreditCard {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String cardNumber;
    
    @Column(nullable = false)
    private String cardHolderName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;
    
    @Column(nullable = false)
    private BigDecimal creditLimit;
    
    @Column(nullable = false)
    private BigDecimal availableCredit;
    
    @Column(nullable = false)
    private BigDecimal outstandingBalance;
    
    private BigDecimal minimumPayment;
    
    private Double interestRate;
    
    @Enumerated(EnumType.STRING)
    private CardStatus status;
    
    private LocalDate issueDate;
    
    private LocalDate expiryDate;
    
    private LocalDate billingDate;
    
    private LocalDate paymentDueDate;
    
    private String cvv;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    public enum CardType {
        SILVER, GOLD, PLATINUM, TITANIUM, SIGNATURE
    }
    
    public enum CardStatus {
        ACTIVE, BLOCKED, EXPIRED, CANCELLED, SUSPENDED
    }
}
