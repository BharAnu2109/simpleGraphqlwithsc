# GraphQL Banking Application

A comprehensive Spring Boot GraphQL application that implements a complete banking system with all complex scenarios including customer management, accounts, transactions, loans, and credit cards with dynamic values.

## Features

### Complex Banking Scenarios Implemented

1. **Customer Management**
   - Multiple customer types (Individual, Business, Premium, VIP)
   - Customer status management (Active, Inactive, Suspended, Blocked)
   - Credit score tracking
   - Full CRUD operations

2. **Account Management**
   - Multiple account types (Savings, Checking, Fixed Deposit, Money Market, Investment)
   - Account status management (Active, Dormant, Closed, Frozen)
   - Overdraft limit support
   - Variable interest rates based on account type
   - Multi-currency support

3. **Transaction Management**
   - Various transaction types (Deposit, Withdrawal, Transfer, Payment, Fee, Interest, Refund)
   - Transaction status tracking (Pending, Completed, Failed, Reversed, Cancelled)
   - Real-time balance updates
   - Transaction history with reference numbers
   - Inter-account transfers

4. **Loan Management**
   - Multiple loan types (Personal, Home, Auto, Education, Business)
   - Loan lifecycle (Pending Approval, Approved, Disbursed, Active, Paid Off, Defaulted, Closed)
   - Dynamic interest rate calculation
   - Monthly payment calculation with amortization
   - Loan payment processing

5. **Credit Card Management**
   - Multiple card types (Silver, Gold, Platinum, Titanium, Signature)
   - Card status management (Active, Blocked, Expired, Cancelled, Suspended)
   - Credit limit and available credit tracking
   - Outstanding balance and minimum payment calculation
   - Variable interest rates based on card type
   - Payment processing

## Technology Stack

- **Java 17**
- **Spring Boot 3.1.5**
- **Spring GraphQL**
- **Spring Data JPA**
- **H2 Database** (In-memory)
- **Lombok**
- **Maven**

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

### Build the Application

```bash
mvn clean package
```

### Run the Application

```bash
java -jar target/graphql-banking-app-1.0.0.jar
```

Or using Maven:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Access GraphiQL UI

Open your browser and navigate to:
```
http://localhost:8080/graphiql
```

### Access H2 Console

```
http://localhost:8080/h2-console
```

Connection details:
- JDBC URL: `jdbc:h2:mem:bankingdb`
- Username: `sa`
- Password: (leave empty)

## Sample Queries

### Customer Queries

#### Get All Customers
```graphql
query {
  getAllCustomers {
    id
    firstName
    lastName
    email
    customerType
    status
    creditScore
  }
}
```

#### Get Customer by ID
```graphql
query {
  getCustomerById(id: 1) {
    id
    firstName
    lastName
    email
    phoneNumber
    address
    accounts {
      accountNumber
      accountType
      balance
    }
    loans {
      loanNumber
      loanType
      outstandingAmount
    }
    creditCards {
      cardNumber
      cardType
      availableCredit
    }
  }
}
```

#### Get Customer Financial Summary
```graphql
query {
  getCustomerFinancialSummary(customerId: 1) {
    customer {
      firstName
      lastName
      email
    }
    totalAccountBalance
    totalLoanOutstanding
    totalCreditCardOutstanding
    totalAvailableCredit
    netWorth
  }
}
```

### Account Queries

#### Get All Accounts
```graphql
query {
  getAllAccounts {
    id
    accountNumber
    accountType
    balance
    currency
    status
    customer {
      firstName
      lastName
    }
  }
}
```

#### Get Account by Number
```graphql
query {
  getAccountByNumber(accountNumber: "ACC1001234567890") {
    id
    accountNumber
    accountType
    balance
    currency
    status
    interestRate
    transactions {
      transactionId
      type
      amount
      status
    }
  }
}
```

### Transaction Queries

#### Get Transactions by Account
```graphql
query {
  getTransactionsByAccountId(accountId: 1) {
    id
    transactionId
    type
    amount
    currency
    description
    status
    transactionDate
  }
}
```

### Loan Queries

#### Get Loans by Customer
```graphql
query {
  getLoansByCustomerId(customerId: 1) {
    id
    loanNumber
    loanType
    principalAmount
    outstandingAmount
    interestRate
    monthlyPayment
    status
  }
}
```

### Credit Card Queries

#### Get Credit Cards by Customer
```graphql
query {
  getCreditCardsByCustomerId(customerId: 1) {
    id
    cardNumber
    cardType
    creditLimit
    availableCredit
    outstandingBalance
    status
  }
}
```

## Sample Mutations

### Customer Mutations

#### Create Customer
```graphql
mutation {
  createCustomer(input: {
    firstName: "Alice"
    lastName: "Williams"
    email: "alice.williams@example.com"
    phoneNumber: "+1-555-0104"
    customerType: INDIVIDUAL
    address: "789 Pine St, Boston, MA 02101"
    nationalId: "111-22-3333"
  }) {
    id
    firstName
    lastName
    email
    status
    creditScore
  }
}
```

#### Update Customer
```graphql
mutation {
  updateCustomer(input: {
    id: 1
    phoneNumber: "+1-555-9999"
    address: "New Address, New York, NY 10001"
    creditScore: 780.0
  }) {
    id
    firstName
    lastName
    phoneNumber
    address
    creditScore
  }
}
```

#### Suspend Customer
```graphql
mutation {
  suspendCustomer(id: 1) {
    id
    firstName
    status
  }
}
```

### Account Mutations

#### Create Account
```graphql
mutation {
  createAccount(input: {
    customerId: 1
    accountType: SAVINGS
    initialBalance: 5000.00
    currency: "USD"
  }) {
    id
    accountNumber
    accountType
    balance
    currency
    status
    interestRate
  }
}
```

#### Freeze Account
```graphql
mutation {
  freezeAccount(id: 1) {
    id
    accountNumber
    status
  }
}
```

### Transaction Mutations

#### Deposit Money
```graphql
mutation {
  depositMoney(
    accountId: 1
    amount: 1000.00
    description: "Monthly salary"
  ) {
    id
    transactionId
    type
    amount
    status
    transactionDate
  }
}
```

#### Withdraw Money
```graphql
mutation {
  withdrawMoney(
    accountId: 1
    amount: 500.00
    description: "ATM withdrawal"
  ) {
    id
    transactionId
    type
    amount
    status
  }
}
```

#### Transfer Money
```graphql
mutation {
  transferMoney(
    fromAccountId: 1
    toAccountId: 2
    amount: 500.00
    description: "Transfer to savings"
  ) {
    id
    transactionId
    type
    amount
    status
    beneficiaryName
    beneficiaryAccount
  }
}
```

### Loan Mutations

#### Apply for Loan
```graphql
mutation {
  applyForLoan(input: {
    customerId: 1
    loanType: PERSONAL
    principalAmount: 15000.00
    interestRate: 7.5
    tenureMonths: 36
  }) {
    id
    loanNumber
    loanType
    principalAmount
    interestRate
    tenureMonths
    monthlyPayment
    status
  }
}
```

#### Approve Loan
```graphql
mutation {
  approveLoan(loanId: 1) {
    id
    loanNumber
    status
  }
}
```

#### Disburse Loan
```graphql
mutation {
  disburseLoan(loanId: 1) {
    id
    loanNumber
    status
    disbursementDate
    maturityDate
  }
}
```

#### Make Loan Payment
```graphql
mutation {
  makeLoanPayment(input: {
    loanId: 1
    paymentAmount: 500.00
  }) {
    id
    loanNumber
    outstandingAmount
    status
    nextPaymentDate
  }
}
```

### Credit Card Mutations

#### Issue Credit Card
```graphql
mutation {
  issueCreditCard(input: {
    customerId: 1
    cardType: PLATINUM
    creditLimit: 10000.00
  }) {
    id
    cardNumber
    cardType
    creditLimit
    availableCredit
    status
    interestRate
  }
}
```

#### Block Credit Card
```graphql
mutation {
  blockCreditCard(cardId: 1) {
    id
    cardNumber
    status
  }
}
```

#### Make Credit Card Payment
```graphql
mutation {
  makeCreditCardPayment(input: {
    cardId: 1
    paymentAmount: 500.00
  }) {
    id
    cardNumber
    outstandingBalance
    availableCredit
    minimumPayment
  }
}
```

## Sample Data

The application comes pre-loaded with sample data:

### Customers
- John Doe (Premium) - Credit Score: 750
- Jane Smith (Individual) - Credit Score: 680
- Robert Johnson (VIP) - Credit Score: 820

### Accounts
- Multiple accounts per customer with different types
- Balances ranging from $3,500 to $50,000

### Transactions
- Sample deposits, withdrawals, and transfers

### Loans
- Home loan: $250,000 (30 years)
- Auto loan: $30,000 (5 years)
- Personal loan: $10,000 (2 years)

### Credit Cards
- Platinum card with $10,000 limit
- Gold card with $5,000 limit
- Signature card with $25,000 limit

## Complex Scenarios Handled

1. **Dynamic Interest Rates**: Different rates for different account and card types
2. **Transaction Validation**: Balance checking, account status verification
3. **Loan Amortization**: Automatic monthly payment calculation
4. **Credit Management**: Real-time credit limit and available credit tracking
5. **Financial Summary**: Comprehensive customer financial position
6. **Multi-entity Relationships**: Complex relationships between customers, accounts, transactions, loans, and cards
7. **Status Management**: Comprehensive status tracking across all entities
8. **Real-time Updates**: Immediate balance and credit updates on transactions

## API Documentation

### GraphQL Endpoint
```
POST http://localhost:8080/graphql
```

### Schema
The complete GraphQL schema is available at:
```
src/main/resources/graphql/schema.graphqls
```

## Project Structure

```
src/main/java/com/banking/graphql/
├── BankingGraphqlApplication.java      # Main application class
├── config/
│   └── DataInitializer.java            # Sample data initialization
├── model/
│   ├── Customer.java                    # Customer entity
│   ├── Account.java                     # Account entity
│   ├── Transaction.java                 # Transaction entity
│   ├── Loan.java                        # Loan entity
│   └── CreditCard.java                  # Credit card entity
├── repository/
│   ├── CustomerRepository.java          # Customer data access
│   ├── AccountRepository.java           # Account data access
│   ├── TransactionRepository.java       # Transaction data access
│   ├── LoanRepository.java              # Loan data access
│   └── CreditCardRepository.java        # Credit card data access
└── resolver/
    ├── QueryResolver.java               # GraphQL queries
    ├── MutationResolver.java            # GraphQL mutations
    └── CustomerFinancialSummary.java    # DTO for financial summary

src/main/resources/
├── graphql/
│   └── schema.graphqls                  # GraphQL schema definition
└── application.properties               # Application configuration
```

## Configuration

Key configuration in `application.properties`:

```properties
# Server Configuration
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:bankingdb
spring.h2.console.enabled=true

# GraphQL
spring.graphql.graphiql.enabled=true
spring.graphql.graphiql.path=/graphiql
spring.graphql.path=/graphql

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

## Testing

You can test the API using:
1. GraphiQL UI at `http://localhost:8080/graphiql`
2. Postman or any GraphQL client
3. cURL commands

Example cURL:
```bash
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ getAllCustomers { id firstName lastName email } }"}'
```

## License

This project is open source and available under the MIT License.

## Author

Created as a comprehensive GraphQL banking application demonstrating complex scenarios with dynamic values.
