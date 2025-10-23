package com.banking.graphql.resolver;

import com.banking.graphql.model.*;
import com.banking.graphql.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class QueryResolver {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LoanRepository loanRepository;
    private final CreditCardRepository creditCardRepository;

    // Customer Queries
    @QueryMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @QueryMapping
    public Customer getCustomerById(@Argument Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public Customer getCustomerByEmail(@Argument String email) {
        return customerRepository.findByEmail(email).orElse(null);
    }

    @QueryMapping
    public List<Customer> getCustomersByStatus(@Argument Customer.CustomerStatus status) {
        return customerRepository.findByStatus(status);
    }

    @QueryMapping
    public List<Customer> getCustomersByType(@Argument Customer.CustomerType customerType) {
        return customerRepository.findByCustomerType(customerType);
    }

    // Account Queries
    @QueryMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @QueryMapping
    public Account getAccountById(@Argument Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public Account getAccountByNumber(@Argument String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElse(null);
    }

    @QueryMapping
    public List<Account> getAccountsByCustomerId(@Argument Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    @QueryMapping
    public List<Account> getAccountsByType(@Argument Account.AccountType accountType) {
        return accountRepository.findByAccountType(accountType);
    }

    // Transaction Queries
    @QueryMapping
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @QueryMapping
    public Transaction getTransactionById(@Argument Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public Transaction getTransactionByTransactionId(@Argument String transactionId) {
        return transactionRepository.findByTransactionId(transactionId).orElse(null);
    }

    @QueryMapping
    public List<Transaction> getTransactionsByAccountId(@Argument Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    @QueryMapping
    public List<Transaction> getTransactionsByType(@Argument Transaction.TransactionType type) {
        return transactionRepository.findByType(type);
    }

    // Loan Queries
    @QueryMapping
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @QueryMapping
    public Loan getLoanById(@Argument Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public Loan getLoanByNumber(@Argument String loanNumber) {
        return loanRepository.findByLoanNumber(loanNumber).orElse(null);
    }

    @QueryMapping
    public List<Loan> getLoansByCustomerId(@Argument Long customerId) {
        return loanRepository.findByCustomerId(customerId);
    }

    @QueryMapping
    public List<Loan> getLoansByStatus(@Argument Loan.LoanStatus status) {
        return loanRepository.findByStatus(status);
    }

    // CreditCard Queries
    @QueryMapping
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    @QueryMapping
    public CreditCard getCreditCardById(@Argument Long id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public CreditCard getCreditCardByNumber(@Argument String cardNumber) {
        return creditCardRepository.findByCardNumber(cardNumber).orElse(null);
    }

    @QueryMapping
    public List<CreditCard> getCreditCardsByCustomerId(@Argument Long customerId) {
        return creditCardRepository.findByCustomerId(customerId);
    }

    @QueryMapping
    public List<CreditCard> getCreditCardsByStatus(@Argument CreditCard.CardStatus status) {
        return creditCardRepository.findByStatus(status);
    }

    // Complex Queries
    @QueryMapping
    public CustomerFinancialSummary getCustomerFinancialSummary(@Argument Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        List<Loan> loans = loanRepository.findByCustomerId(customerId);
        List<CreditCard> cards = creditCardRepository.findByCustomerId(customerId);

        BigDecimal totalAccountBalance = accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalLoanOutstanding = loans.stream()
                .map(Loan::getOutstandingAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCreditCardOutstanding = cards.stream()
                .map(CreditCard::getOutstandingBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAvailableCredit = cards.stream()
                .map(CreditCard::getAvailableCredit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netWorth = totalAccountBalance
                .subtract(totalLoanOutstanding)
                .subtract(totalCreditCardOutstanding);

        return CustomerFinancialSummary.builder()
                .customer(customer)
                .totalAccountBalance(totalAccountBalance.doubleValue())
                .totalLoanOutstanding(totalLoanOutstanding.doubleValue())
                .totalCreditCardOutstanding(totalCreditCardOutstanding.doubleValue())
                .totalAvailableCredit(totalAvailableCredit.doubleValue())
                .netWorth(netWorth.doubleValue())
                .build();
    }

    @QueryMapping
    public Double getAccountBalance(@Argument String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance().doubleValue();
    }
}
