package com.banking.graphql.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    private LocalDate dateOfBirth;
    
    private String address;
    
    @Column(unique = true)
    private String nationalId;
    
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
    
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
    
    private Double creditScore;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CreditCard> creditCards;
    
    public enum CustomerStatus {
        ACTIVE, INACTIVE, SUSPENDED, BLOCKED
    }
    
    public enum CustomerType {
        INDIVIDUAL, BUSINESS, PREMIUM, VIP
    }
}
