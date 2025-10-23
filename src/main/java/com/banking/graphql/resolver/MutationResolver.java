package com.banking.graphql.resolver;

import com.banking.graphql.model.*;
import com.banking.graphql.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MutationResolver {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LoanRepository loanRepository;
    private final CreditCardRepository creditCardRepository;

    // Customer Mutations
    @MutationMapping
    public Customer createCustomer(@Argument Map<String, Object> input) {
        Customer customer = Customer.builder()
                .firstName((String) input.get("firstName"))
                .lastName((String) input.get("lastName"))
                .email((String) input.get("email"))
                .phoneNumber((String) input.get("phoneNumber"))
                .dateOfBirth(input.containsKey("dateOfBirth") ? 
                        LocalDate.parse((String) input.get("dateOfBirth")) : null)
                .address((String) input.get("address"))
                .nationalId((String) input.get("nationalId"))
                .status(Customer.CustomerStatus.ACTIVE)
                .customerType(Customer.CustomerType.valueOf((String) input.get("customerType")))
                .creditScore(700.0)
                .build();
        return customerRepository.save(customer);
    }

    @MutationMapping
    public Customer updateCustomer(@Argument Map<String, Object> input) {
        Long id = Long.parseLong(input.get("id").toString());
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (input.containsKey("firstName")) {
            customer.setFirstName((String) input.get("firstName"));
        }
        if (input.containsKey("lastName")) {
            customer.setLastName((String) input.get("lastName"));
        }
        if (input.containsKey("phoneNumber")) {
            customer.setPhoneNumber((String) input.get("phoneNumber"));
        }
        if (input.containsKey("address")) {
            customer.setAddress((String) input.get("address"));
        }
        if (input.containsKey("status")) {
            customer.setStatus(Customer.CustomerStatus.valueOf((String) input.get("status")));
        }
        if (input.containsKey("creditScore")) {
            customer.setCreditScore(((Number) input.get("creditScore")).doubleValue());
        }

        return customerRepository.save(customer);
    }

    @MutationMapping
    public Boolean deleteCustomer(@Argument Long id) {
        customerRepository.deleteById(id);
        return true;
    }

    @MutationMapping
    public Customer suspendCustomer(@Argument Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setStatus(Customer.CustomerStatus.SUSPENDED);
        return customerRepository.save(customer);
    }

    @MutationMapping
    public Customer activateCustomer(@Argument Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setStatus(Customer.CustomerStatus.ACTIVE);
        return customerRepository.save(customer);
    }

    // Account Mutations
    @MutationMapping
    public Account createAccount(@Argument Map<String, Object> input) {
        Long customerId = Long.parseLong(input.get("customerId").toString());
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        BigDecimal initialBalance = new BigDecimal(input.get("initialBalance").toString());
        
        Account account = Account.builder()
                .accountNumber(generateAccountNumber())
                .accountType(Account.AccountType.valueOf((String) input.get("accountType")))
                .balance(initialBalance)
                .currency((String) input.get("currency"))
                .status(Account.AccountStatus.ACTIVE)
                .overdraftLimit(input.containsKey("overdraftLimit") ? 
                        new BigDecimal(input.get("overdraftLimit").toString()) : BigDecimal.ZERO)
                .interestRate(getInterestRateForAccountType((String) input.get("accountType")))
                .openedDate(LocalDateTime.now())
                .lastTransactionDate(LocalDateTime.now())
                .customer(customer)
                .build();

        return accountRepository.save(account);
    }

    @MutationMapping
    public Account closeAccount(@Argument Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setStatus(Account.AccountStatus.CLOSED);
        return accountRepository.save(account);
    }

    @MutationMapping
    public Account freezeAccount(@Argument Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setStatus(Account.AccountStatus.FROZEN);
        return accountRepository.save(account);
    }

    @MutationMapping
    public Account unfreezeAccount(@Argument Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setStatus(Account.AccountStatus.ACTIVE);
        return accountRepository.save(account);
    }

    // Transaction Mutations
    @MutationMapping
    public Transaction createTransaction(@Argument Map<String, Object> input) {
        Long accountId = Long.parseLong(input.get("accountId").toString());
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal amount = new BigDecimal(input.get("amount").toString());
        Transaction.TransactionType type = Transaction.TransactionType.valueOf((String) input.get("type"));

        Transaction transaction = Transaction.builder()
                .transactionId(generateTransactionId())
                .type(type)
                .amount(amount)
                .currency((String) input.get("currency"))
                .description((String) input.get("description"))
                .status(Transaction.TransactionStatus.COMPLETED)
                .transactionDate(LocalDateTime.now())
                .referenceNumber(UUID.randomUUID().toString())
                .beneficiaryName((String) input.get("beneficiaryName"))
                .beneficiaryAccount((String) input.get("beneficiaryAccount"))
                .account(account)
                .build();

        if (input.containsKey("destinationAccountId")) {
            Long destAccountId = Long.parseLong(input.get("destinationAccountId").toString());
            Account destAccount = accountRepository.findById(destAccountId).orElse(null);
            transaction.setDestinationAccount(destAccount);
        }

        // Update account balance
        updateAccountBalance(account, amount, type);
        account.setLastTransactionDate(LocalDateTime.now());
        accountRepository.save(account);

        return transactionRepository.save(transaction);
    }

    @MutationMapping
    public Transaction depositMoney(@Argument Long accountId, @Argument Double amount, 
                                    @Argument String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal depositAmount = BigDecimal.valueOf(amount);
        account.setBalance(account.getBalance().add(depositAmount));
        account.setLastTransactionDate(LocalDateTime.now());
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .transactionId(generateTransactionId())
                .type(Transaction.TransactionType.DEPOSIT)
                .amount(depositAmount)
                .currency(account.getCurrency())
                .description(description)
                .status(Transaction.TransactionStatus.COMPLETED)
                .transactionDate(LocalDateTime.now())
                .referenceNumber(UUID.randomUUID().toString())
                .account(account)
                .build();

        return transactionRepository.save(transaction);
    }

    @MutationMapping
    public Transaction withdrawMoney(@Argument Long accountId, @Argument Double amount, 
                                     @Argument String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal withdrawAmount = BigDecimal.valueOf(amount);
        
        if (account.getBalance().compareTo(withdrawAmount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(withdrawAmount));
        account.setLastTransactionDate(LocalDateTime.now());
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .transactionId(generateTransactionId())
                .type(Transaction.TransactionType.WITHDRAWAL)
                .amount(withdrawAmount)
                .currency(account.getCurrency())
                .description(description)
                .status(Transaction.TransactionStatus.COMPLETED)
                .transactionDate(LocalDateTime.now())
                .referenceNumber(UUID.randomUUID().toString())
                .account(account)
                .build();

        return transactionRepository.save(transaction);
    }

    @MutationMapping
    public Transaction transferMoney(@Argument Long fromAccountId, @Argument Long toAccountId, 
                                     @Argument Double amount, @Argument String description) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        BigDecimal transferAmount = BigDecimal.valueOf(amount);
        
        if (fromAccount.getBalance().compareTo(transferAmount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
        toAccount.setBalance(toAccount.getBalance().add(transferAmount));
        fromAccount.setLastTransactionDate(LocalDateTime.now());
        toAccount.setLastTransactionDate(LocalDateTime.now());
        
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = Transaction.builder()
                .transactionId(generateTransactionId())
                .type(Transaction.TransactionType.TRANSFER)
                .amount(transferAmount)
                .currency(fromAccount.getCurrency())
                .description(description)
                .status(Transaction.TransactionStatus.COMPLETED)
                .transactionDate(LocalDateTime.now())
                .referenceNumber(UUID.randomUUID().toString())
                .account(fromAccount)
                .destinationAccount(toAccount)
                .beneficiaryName(toAccount.getCustomer().getFirstName() + " " + 
                                 toAccount.getCustomer().getLastName())
                .beneficiaryAccount(toAccount.getAccountNumber())
                .build();

        return transactionRepository.save(transaction);
    }

    // Loan Mutations
    @MutationMapping
    public Loan applyForLoan(@Argument Map<String, Object> input) {
        Long customerId = Long.parseLong(input.get("customerId").toString());
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        BigDecimal principalAmount = new BigDecimal(input.get("principalAmount").toString());
        Double interestRate = ((Number) input.get("interestRate")).doubleValue();
        Integer tenureMonths = ((Number) input.get("tenureMonths")).intValue();

        BigDecimal monthlyPayment = calculateMonthlyPayment(principalAmount, interestRate, tenureMonths);

        Loan loan = Loan.builder()
                .loanNumber(generateLoanNumber())
                .loanType(Loan.LoanType.valueOf((String) input.get("loanType")))
                .principalAmount(principalAmount)
                .outstandingAmount(principalAmount)
                .interestRate(interestRate)
                .tenureMonths(tenureMonths)
                .monthlyPayment(monthlyPayment)
                .status(Loan.LoanStatus.PENDING_APPROVAL)
                .customer(customer)
                .build();

        return loanRepository.save(loan);
    }

    @MutationMapping
    public Loan approveLoan(@Argument Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        loan.setStatus(Loan.LoanStatus.APPROVED);
        return loanRepository.save(loan);
    }

    @MutationMapping
    public Loan disburseLoan(@Argument Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        
        if (loan.getStatus() != Loan.LoanStatus.APPROVED) {
            throw new RuntimeException("Loan must be approved before disbursement");
        }

        loan.setStatus(Loan.LoanStatus.DISBURSED);
        loan.setDisbursementDate(LocalDate.now());
        loan.setMaturityDate(LocalDate.now().plusMonths(loan.getTenureMonths()));
        loan.setNextPaymentDate(LocalDate.now().plusMonths(1));
        
        return loanRepository.save(loan);
    }

    @MutationMapping
    public Loan makeLoanPayment(@Argument Map<String, Object> input) {
        Long loanId = Long.parseLong(input.get("loanId").toString());
        BigDecimal paymentAmount = new BigDecimal(input.get("paymentAmount").toString());

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        BigDecimal newOutstanding = loan.getOutstandingAmount().subtract(paymentAmount);
        loan.setOutstandingAmount(newOutstanding);

        if (newOutstanding.compareTo(BigDecimal.ZERO) <= 0) {
            loan.setStatus(Loan.LoanStatus.PAID_OFF);
        } else {
            loan.setStatus(Loan.LoanStatus.ACTIVE);
            loan.setNextPaymentDate(LocalDate.now().plusMonths(1));
        }

        return loanRepository.save(loan);
    }

    // CreditCard Mutations
    @MutationMapping
    public CreditCard issueCreditCard(@Argument Map<String, Object> input) {
        Long customerId = Long.parseLong(input.get("customerId").toString());
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        BigDecimal creditLimit = new BigDecimal(input.get("creditLimit").toString());
        CreditCard.CardType cardType = CreditCard.CardType.valueOf((String) input.get("cardType"));

        CreditCard card = CreditCard.builder()
                .cardNumber(generateCardNumber())
                .cardHolderName(customer.getFirstName() + " " + customer.getLastName())
                .cardType(cardType)
                .creditLimit(creditLimit)
                .availableCredit(creditLimit)
                .outstandingBalance(BigDecimal.ZERO)
                .minimumPayment(BigDecimal.ZERO)
                .interestRate(getInterestRateForCardType(cardType))
                .status(CreditCard.CardStatus.ACTIVE)
                .issueDate(LocalDate.now())
                .expiryDate(LocalDate.now().plusYears(5))
                .billingDate(LocalDate.now().withDayOfMonth(1))
                .paymentDueDate(LocalDate.now().withDayOfMonth(15))
                .cvv(generateCVV())
                .customer(customer)
                .build();

        return creditCardRepository.save(card);
    }

    @MutationMapping
    public CreditCard blockCreditCard(@Argument Long cardId) {
        CreditCard card = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Credit card not found"));
        card.setStatus(CreditCard.CardStatus.BLOCKED);
        return creditCardRepository.save(card);
    }

    @MutationMapping
    public CreditCard unblockCreditCard(@Argument Long cardId) {
        CreditCard card = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Credit card not found"));
        card.setStatus(CreditCard.CardStatus.ACTIVE);
        return creditCardRepository.save(card);
    }

    @MutationMapping
    public CreditCard makeCreditCardPayment(@Argument Map<String, Object> input) {
        Long cardId = Long.parseLong(input.get("cardId").toString());
        BigDecimal paymentAmount = new BigDecimal(input.get("paymentAmount").toString());

        CreditCard card = creditCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Credit card not found"));

        BigDecimal newOutstanding = card.getOutstandingBalance().subtract(paymentAmount);
        card.setOutstandingBalance(newOutstanding.max(BigDecimal.ZERO));
        
        BigDecimal newAvailableCredit = card.getCreditLimit().subtract(card.getOutstandingBalance());
        card.setAvailableCredit(newAvailableCredit);
        
        BigDecimal newMinimumPayment = card.getOutstandingBalance()
                .multiply(BigDecimal.valueOf(0.05));
        card.setMinimumPayment(newMinimumPayment);

        return creditCardRepository.save(card);
    }

    // Helper methods
    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    private String generateLoanNumber() {
        return "LOAN" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    private String generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append((int)(Math.random() * 10));
        }
        return sb.toString();
    }

    private String generateCVV() {
        return String.format("%03d", (int)(Math.random() * 1000));
    }

    private Double getInterestRateForAccountType(String accountType) {
        return switch (Account.AccountType.valueOf(accountType)) {
            case SAVINGS -> 3.5;
            case CHECKING -> 0.5;
            case FIXED_DEPOSIT -> 6.5;
            case MONEY_MARKET -> 4.5;
            case INVESTMENT -> 5.5;
        };
    }

    private Double getInterestRateForCardType(CreditCard.CardType cardType) {
        return switch (cardType) {
            case SILVER -> 18.0;
            case GOLD -> 16.0;
            case PLATINUM -> 14.0;
            case TITANIUM -> 12.0;
            case SIGNATURE -> 10.0;
        };
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal principal, Double annualRate, Integer months) {
        double monthlyRate = annualRate / 12 / 100;
        double factor = Math.pow(1 + monthlyRate, months);
        double monthlyPayment = principal.doubleValue() * (monthlyRate * factor) / (factor - 1);
        return BigDecimal.valueOf(monthlyPayment);
    }

    private void updateAccountBalance(Account account, BigDecimal amount, Transaction.TransactionType type) {
        switch (type) {
            case DEPOSIT, INTEREST, REFUND -> account.setBalance(account.getBalance().add(amount));
            case WITHDRAWAL, TRANSFER, PAYMENT, FEE -> account.setBalance(account.getBalance().subtract(amount));
        }
    }
}
