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
@Table(name = "loans")
public class Loan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String loanNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanType loanType;
    
    @Column(nullable = false)
    private BigDecimal principalAmount;
    
    @Column(nullable = false)
    private BigDecimal outstandingAmount;
    
    @Column(nullable = false)
    private Double interestRate;
    
    private Integer tenureMonths;
    
    private BigDecimal monthlyPayment;
    
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    
    private LocalDate disbursementDate;
    
    private LocalDate maturityDate;
    
    private LocalDate nextPaymentDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    public enum LoanType {
        PERSONAL, HOME, AUTO, EDUCATION, BUSINESS
    }
    
    public enum LoanStatus {
        PENDING_APPROVAL, APPROVED, DISBURSED, ACTIVE, PAID_OFF, DEFAULTED, CLOSED
    }
}
