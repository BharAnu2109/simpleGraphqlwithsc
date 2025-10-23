package com.banking.graphql.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String accountNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;
    
    @Column(nullable = false)
    private BigDecimal balance;
    
    @Column(nullable = false)
    private String currency;
    
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    
    private BigDecimal overdraftLimit;
    
    private Double interestRate;
    
    private LocalDateTime openedDate;
    
    private LocalDateTime lastTransactionDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
    
    public enum AccountType {
        SAVINGS, CHECKING, FIXED_DEPOSIT, MONEY_MARKET, INVESTMENT
    }
    
    public enum AccountStatus {
        ACTIVE, DORMANT, CLOSED, FROZEN
    }
}
