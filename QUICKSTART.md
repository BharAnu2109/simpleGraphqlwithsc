# Quick Start Guide

## Running the Application

1. **Build the project:**
   ```bash
   mvn clean package
   ```

2. **Run the application:**
   ```bash
   java -jar target/graphql-banking-app-1.0.0.jar
   ```

3. **Access GraphiQL UI:**
   Open browser: http://localhost:8080/graphiql

## Quick Test Scenarios

### Scenario 1: View All Customers
```graphql
query {
  getAllCustomers {
    id firstName lastName email customerType creditScore
  }
}
```

### Scenario 2: Create New Customer and Account
```graphql
# Step 1: Create Customer
mutation {
  createCustomer(input: {
    firstName: "Test"
    lastName: "User"
    email: "test@example.com"
    phoneNumber: "+1-555-1234"
    customerType: INDIVIDUAL
  }) {
    id firstName lastName email
  }
}

# Step 2: Create Account (use customer ID from step 1)
mutation {
  createAccount(input: {
    customerId: 4
    accountType: CHECKING
    initialBalance: 1000.00
    currency: "USD"
  }) {
    id accountNumber balance
  }
}
```

### Scenario 3: Banking Operations
```graphql
# Deposit Money
mutation {
  depositMoney(accountId: 1, amount: 1000.00, description: "Salary") {
    id transactionId amount status
  }
}

# Withdraw Money
mutation {
  withdrawMoney(accountId: 1, amount: 200.00, description: "ATM") {
    id transactionId amount status
  }
}

# Transfer Money
mutation {
  transferMoney(
    fromAccountId: 1
    toAccountId: 2
    amount: 500.00
    description: "Transfer"
  ) {
    id transactionId amount status beneficiaryName
  }
}
```

### Scenario 4: Loan Application
```graphql
# Apply for Loan
mutation {
  applyForLoan(input: {
    customerId: 1
    loanType: AUTO
    principalAmount: 25000.00
    interestRate: 5.5
    tenureMonths: 60
  }) {
    id loanNumber loanType monthlyPayment status
  }
}

# Approve Loan
mutation {
  approveLoan(loanId: 1) {
    id loanNumber status
  }
}

# Disburse Loan
mutation {
  disburseLoan(loanId: 1) {
    id loanNumber status disbursementDate
  }
}
```

### Scenario 5: Credit Card Management
```graphql
# Issue Credit Card
mutation {
  issueCreditCard(input: {
    customerId: 1
    cardType: GOLD
    creditLimit: 5000.00
  }) {
    id cardNumber cardType creditLimit availableCredit
  }
}

# Make Payment
mutation {
  makeCreditCardPayment(input: {
    cardId: 1
    paymentAmount: 500.00
  }) {
    id cardNumber outstandingBalance availableCredit
  }
}
```

### Scenario 6: Financial Summary
```graphql
query {
  getCustomerFinancialSummary(customerId: 1) {
    customer { firstName lastName }
    totalAccountBalance
    totalLoanOutstanding
    totalCreditCardOutstanding
    netWorth
  }
}
```

## Sample cURL Commands

```bash
# Get All Customers
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ getAllCustomers { id firstName lastName email } }"}'

# Create Customer
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "mutation { createCustomer(input: { firstName: \"John\", lastName: \"Doe\", email: \"john@example.com\", phoneNumber: \"+1-555-0000\", customerType: INDIVIDUAL }) { id firstName lastName } }"}'

# Deposit Money
curl -X POST http://localhost:8080/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "mutation { depositMoney(accountId: 1, amount: 1000.00, description: \"Deposit\") { id transactionId amount status } }"}'
```

## Testing with Postman

1. Create a new POST request to `http://localhost:8080/graphql`
2. Set Content-Type header to `application/json`
3. In the body, use raw JSON format:
   ```json
   {
     "query": "{ getAllCustomers { id firstName lastName email } }"
   }
   ```

## Pre-loaded Sample Data

- 3 Customers (John Doe, Jane Smith, Robert Johnson)
- 4 Accounts with varying balances
- 3 Transactions
- 3 Loans (Home, Auto, Personal)
- 3 Credit Cards (Platinum, Gold, Signature)

## Troubleshooting

### Port 8080 already in use
Change the port in `src/main/resources/application.properties`:
```properties
server.port=8081
```

### H2 Console Access
URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:bankingdb
- Username: sa
- Password: (empty)

## Next Steps

1. Explore the GraphQL schema in GraphiQL
2. Try the sample queries and mutations
3. Create your own test scenarios
4. Check the H2 console to see the data
5. Review the code to understand the implementation
