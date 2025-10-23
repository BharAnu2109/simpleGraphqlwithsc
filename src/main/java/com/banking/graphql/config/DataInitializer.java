package com.banking.graphql.config;

import com.banking.graphql.model.*;
import com.banking.graphql.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LoanRepository loanRepository;
    private final CreditCardRepository creditCardRepository;

    @Override
    public void run(String... args) {
        // Create sample customers
        Customer customer1 = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("+1-555-0101")
                .dateOfBirth(LocalDate.of(1985, 5, 15))
                .address("123 Main St, New York, NY 10001")
                .nationalId("123-45-6789")
                .status(Customer.CustomerStatus.ACTIVE)
                .customerType(Customer.CustomerType.PREMIUM)
                .creditScore(750.0)
                .build();

        Customer customer2 = Customer.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .phoneNumber("+1-555-0102")
                .dateOfBirth(LocalDate.of(1990, 8, 22))
                .address("456 Oak Ave, Los Angeles, CA 90001")
                .nationalId("987-65-4321")
                .status(Customer.CustomerStatus.ACTIVE)
                .customerType(Customer.CustomerType.INDIVIDUAL)
                .creditScore(680.0)
                .build();

        Customer customer3 = Customer.builder()
                .firstName("Robert")
                .lastName("Johnson")
                .email("robert.johnson@example.com")
                .phoneNumber("+1-555-0103")
                .dateOfBirth(LocalDate.of(1978, 3, 10))
                .address("789 Elm St, Chicago, IL 60601")
                .nationalId("555-12-3456")
                .status(Customer.CustomerStatus.ACTIVE)
                .customerType(Customer.CustomerType.VIP)
                .creditScore(820.0)
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        // Create accounts
        Account account1 = Account.builder()
                .accountNumber("ACC1001234567890")
                .accountType(Account.AccountType.CHECKING)
                .balance(new BigDecimal("5000.00"))
                .currency("USD")
                .status(Account.AccountStatus.ACTIVE)
                .overdraftLimit(new BigDecimal("1000.00"))
                .interestRate(0.5)
                .openedDate(LocalDateTime.now().minusYears(2))
                .lastTransactionDate(LocalDateTime.now())
                .customer(customer1)
                .build();

        Account account2 = Account.builder()
                .accountNumber("ACC1001234567891")
                .accountType(Account.AccountType.SAVINGS)
                .balance(new BigDecimal("15000.00"))
                .currency("USD")
                .status(Account.AccountStatus.ACTIVE)
                .interestRate(3.5)
                .openedDate(LocalDateTime.now().minusYears(3))
                .lastTransactionDate(LocalDateTime.now())
                .customer(customer1)
                .build();

        Account account3 = Account.builder()
                .accountNumber("ACC1001234567892")
                .accountType(Account.AccountType.CHECKING)
                .balance(new BigDecimal("3500.00"))
                .currency("USD")
                .status(Account.AccountStatus.ACTIVE)
                .overdraftLimit(new BigDecimal("500.00"))
                .interestRate(0.5)
                .openedDate(LocalDateTime.now().minusYears(1))
                .lastTransactionDate(LocalDateTime.now())
                .customer(customer2)
                .build();

        Account account4 = Account.builder()
                .accountNumber("ACC1001234567893")
                .accountType(Account.AccountType.INVESTMENT)
                .balance(new BigDecimal("50000.00"))
                .currency("USD")
                .status(Account.AccountStatus.ACTIVE)
                .interestRate(5.5)
                .openedDate(LocalDateTime.now().minusYears(5))
                .lastTransactionDate(LocalDateTime.now())
                .customer(customer3)
                .build();

        accountRepository.save(account1);
        accountRepository.save(account2);
        accountRepository.save(account3);
        accountRepository.save(account4);

        // Create transactions
        Transaction transaction1 = Transaction.builder()
                .transactionId("TXN1001")
                .type(Transaction.TransactionType.DEPOSIT)
                .amount(new BigDecimal("1000.00"))
                .currency("USD")
                .description("Salary deposit")
                .status(Transaction.TransactionStatus.COMPLETED)
                .transactionDate(LocalDateTime.now().minusDays(5))
                .referenceNumber("REF001")
                .account(account1)
                .build();

        Transaction transaction2 = Transaction.builder()
                .transactionId("TXN1002")
                .type(Transaction.TransactionType.WITHDRAWAL)
                .amount(new BigDecimal("200.00"))
                .currency("USD")
                .description("ATM withdrawal")
                .status(Transaction.TransactionStatus.COMPLETED)
                .transactionDate(LocalDateTime.now().minusDays(3))
                .referenceNumber("REF002")
                .account(account1)
                .build();

        Transaction transaction3 = Transaction.builder()
                .transactionId("TXN1003")
                .type(Transaction.TransactionType.TRANSFER)
                .amount(new BigDecimal("500.00"))
                .currency("USD")
                .description("Transfer to savings")
                .status(Transaction.TransactionStatus.COMPLETED)
                .transactionDate(LocalDateTime.now().minusDays(1))
                .referenceNumber("REF003")
                .account(account1)
                .destinationAccount(account2)
                .beneficiaryName("John Doe Savings")
                .beneficiaryAccount("ACC1001234567891")
                .build();

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);

        // Create loans
        Loan loan1 = Loan.builder()
                .loanNumber("LOAN1001")
                .loanType(Loan.LoanType.HOME)
                .principalAmount(new BigDecimal("250000.00"))
                .outstandingAmount(new BigDecimal("230000.00"))
                .interestRate(4.5)
                .tenureMonths(360)
                .monthlyPayment(new BigDecimal("1266.71"))
                .status(Loan.LoanStatus.ACTIVE)
                .disbursementDate(LocalDate.now().minusYears(2))
                .maturityDate(LocalDate.now().plusYears(28))
                .nextPaymentDate(LocalDate.now().plusMonths(1))
                .customer(customer1)
                .build();

        Loan loan2 = Loan.builder()
                .loanNumber("LOAN1002")
                .loanType(Loan.LoanType.AUTO)
                .principalAmount(new BigDecimal("30000.00"))
                .outstandingAmount(new BigDecimal("18000.00"))
                .interestRate(6.5)
                .tenureMonths(60)
                .monthlyPayment(new BigDecimal("590.50"))
                .status(Loan.LoanStatus.ACTIVE)
                .disbursementDate(LocalDate.now().minusYears(1))
                .maturityDate(LocalDate.now().plusYears(4))
                .nextPaymentDate(LocalDate.now().plusMonths(1))
                .customer(customer2)
                .build();

        Loan loan3 = Loan.builder()
                .loanNumber("LOAN1003")
                .loanType(Loan.LoanType.PERSONAL)
                .principalAmount(new BigDecimal("10000.00"))
                .outstandingAmount(new BigDecimal("10000.00"))
                .interestRate(8.5)
                .tenureMonths(24)
                .monthlyPayment(new BigDecimal("456.84"))
                .status(Loan.LoanStatus.APPROVED)
                .customer(customer3)
                .build();

        loanRepository.save(loan1);
        loanRepository.save(loan2);
        loanRepository.save(loan3);

        // Create credit cards
        CreditCard card1 = CreditCard.builder()
                .cardNumber("4532123456789012")
                .cardHolderName("John Doe")
                .cardType(CreditCard.CardType.PLATINUM)
                .creditLimit(new BigDecimal("10000.00"))
                .availableCredit(new BigDecimal("8500.00"))
                .outstandingBalance(new BigDecimal("1500.00"))
                .minimumPayment(new BigDecimal("75.00"))
                .interestRate(14.0)
                .status(CreditCard.CardStatus.ACTIVE)
                .issueDate(LocalDate.now().minusYears(2))
                .expiryDate(LocalDate.now().plusYears(3))
                .billingDate(LocalDate.now().withDayOfMonth(1))
                .paymentDueDate(LocalDate.now().withDayOfMonth(15))
                .cvv("123")
                .customer(customer1)
                .build();

        CreditCard card2 = CreditCard.builder()
                .cardNumber("5432123456789013")
                .cardHolderName("Jane Smith")
                .cardType(CreditCard.CardType.GOLD)
                .creditLimit(new BigDecimal("5000.00"))
                .availableCredit(new BigDecimal("4200.00"))
                .outstandingBalance(new BigDecimal("800.00"))
                .minimumPayment(new BigDecimal("40.00"))
                .interestRate(16.0)
                .status(CreditCard.CardStatus.ACTIVE)
                .issueDate(LocalDate.now().minusYears(1))
                .expiryDate(LocalDate.now().plusYears(4))
                .billingDate(LocalDate.now().withDayOfMonth(1))
                .paymentDueDate(LocalDate.now().withDayOfMonth(15))
                .cvv("456")
                .customer(customer2)
                .build();

        CreditCard card3 = CreditCard.builder()
                .cardNumber("3782123456789014")
                .cardHolderName("Robert Johnson")
                .cardType(CreditCard.CardType.SIGNATURE)
                .creditLimit(new BigDecimal("25000.00"))
                .availableCredit(new BigDecimal("25000.00"))
                .outstandingBalance(new BigDecimal("0.00"))
                .minimumPayment(new BigDecimal("0.00"))
                .interestRate(10.0)
                .status(CreditCard.CardStatus.ACTIVE)
                .issueDate(LocalDate.now().minusMonths(6))
                .expiryDate(LocalDate.now().plusYears(5))
                .billingDate(LocalDate.now().withDayOfMonth(1))
                .paymentDueDate(LocalDate.now().withDayOfMonth(15))
                .cvv("789")
                .customer(customer3)
                .build();

        creditCardRepository.save(card1);
        creditCardRepository.save(card2);
        creditCardRepository.save(card3);

        System.out.println("Sample data initialized successfully!");
    }
}
