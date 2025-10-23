# GraphQL API Examples

This file contains ready-to-use GraphQL queries and mutations for testing the Banking Application.

## Customer Operations

### Query: Get All Customers
```graphql
query {
  getAllCustomers {
    id
    firstName
    lastName
    email
    phoneNumber
    dateOfBirth
    address
    customerType
    status
    creditScore
  }
}
```

### Query: Get Customer by ID with Related Data
```graphql
query {
  getCustomerById(id: 1) {
    id
    firstName
    lastName
    email
    phoneNumber
    customerType
    status
    creditScore
    accounts {
      accountNumber
      accountType
      balance
      currency
      status
    }
    loans {
      loanNumber
      loanType
      principalAmount
      outstandingAmount
      status
    }
    creditCards {
      cardNumber
      cardType
      creditLimit
      availableCredit
      status
    }
  }
}
```

### Query: Get Customer by Email
```graphql
query {
  getCustomerByEmail(email: "john.doe@example.com") {
    id
    firstName
    lastName
    email
    customerType
    status
  }
}
```

### Query: Get Customers by Status
```graphql
query {
  getCustomersByStatus(status: ACTIVE) {
    id
    firstName
    lastName
    email
    status
  }
}
```

### Query: Get Customer Financial Summary
```graphql
query {
  getCustomerFinancialSummary(customerId: 1) {
    customer {
      id
      firstName
      lastName
      email
      customerType
    }
    totalAccountBalance
    totalLoanOutstanding
    totalCreditCardOutstanding
    totalAvailableCredit
    netWorth
  }
}
```

### Mutation: Create Customer
```graphql
mutation {
  createCustomer(input: {
    firstName: "Michael"
    lastName: "Brown"
    email: "michael.brown@example.com"
    phoneNumber: "+1-555-0105"
    dateOfBirth: "1988-07-20"
    address: "321 Maple Ave, Seattle, WA 98101"
    nationalId: "444-55-6666"
    customerType: BUSINESS
  }) {
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

### Mutation: Update Customer
```graphql
mutation {
  updateCustomer(input: {
    id: 1
    phoneNumber: "+1-555-9999"
    address: "456 New Street, New York, NY 10002"
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

### Mutation: Suspend Customer
```graphql
mutation {
  suspendCustomer(id: 2) {
    id
    firstName
    lastName
    status
  }
}
```

### Mutation: Activate Customer
```graphql
mutation {
  activateCustomer(id: 2) {
    id
    firstName
    lastName
    status
  }
}
```

## Account Operations

### Query: Get All Accounts
```graphql
query {
  getAllAccounts {
    id
    accountNumber
    accountType
    balance
    currency
    status
    interestRate
    customer {
      firstName
      lastName
      email
    }
  }
}
```

### Query: Get Account by Number
```graphql
query {
  getAccountByNumber(accountNumber: "ACC1001234567890") {
    id
    accountNumber
    accountType
    balance
    currency
    status
    overdraftLimit
    interestRate
    openedDate
    lastTransactionDate
    customer {
      firstName
      lastName
    }
    transactions {
      transactionId
      type
      amount
      description
      status
      transactionDate
    }
  }
}
```

### Query: Get Accounts by Customer ID
```graphql
query {
  getAccountsByCustomerId(customerId: 1) {
    id
    accountNumber
    accountType
    balance
    currency
    status
  }
}
```

### Query: Get Account Balance
```graphql
query {
  getAccountBalance(accountNumber: "ACC1001234567890")
}
```

### Mutation: Create Account
```graphql
mutation {
  createAccount(input: {
    customerId: 1
    accountType: FIXED_DEPOSIT
    initialBalance: 25000.00
    currency: "USD"
  }) {
    id
    accountNumber
    accountType
    balance
    currency
    status
    interestRate
    openedDate
  }
}
```

### Mutation: Freeze Account
```graphql
mutation {
  freezeAccount(id: 1) {
    id
    accountNumber
    status
  }
}
```

### Mutation: Unfreeze Account
```graphql
mutation {
  unfreezeAccount(id: 1) {
    id
    accountNumber
    status
  }
}
```

### Mutation: Close Account
```graphql
mutation {
  closeAccount(id: 1) {
    id
    accountNumber
    status
  }
}
```

## Transaction Operations

### Query: Get All Transactions
```graphql
query {
  getAllTransactions {
    id
    transactionId
    type
    amount
    currency
    description
    status
    transactionDate
    referenceNumber
    beneficiaryName
  }
}
```

### Query: Get Transactions by Account ID
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
    referenceNumber
  }
}
```

### Query: Get Transactions by Type
```graphql
query {
  getTransactionsByType(type: DEPOSIT) {
    id
    transactionId
    type
    amount
    description
    status
    transactionDate
  }
}
```

### Mutation: Deposit Money
```graphql
mutation {
  depositMoney(
    accountId: 1
    amount: 2500.00
    description: "Monthly salary deposit"
  ) {
    id
    transactionId
    type
    amount
    currency
    description
    status
    transactionDate
    referenceNumber
  }
}
```

### Mutation: Withdraw Money
```graphql
mutation {
  withdrawMoney(
    accountId: 1
    amount: 300.00
    description: "Cash withdrawal from ATM"
  ) {
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

### Mutation: Transfer Money Between Accounts
```graphql
mutation {
  transferMoney(
    fromAccountId: 1
    toAccountId: 2
    amount: 1000.00
    description: "Transfer to savings account"
  ) {
    id
    transactionId
    type
    amount
    currency
    description
    status
    transactionDate
    beneficiaryName
    beneficiaryAccount
    referenceNumber
  }
}
```

## Loan Operations

### Query: Get All Loans
```graphql
query {
  getAllLoans {
    id
    loanNumber
    loanType
    principalAmount
    outstandingAmount
    interestRate
    tenureMonths
    monthlyPayment
    status
    disbursementDate
    maturityDate
    customer {
      firstName
      lastName
    }
  }
}
```

### Query: Get Loans by Customer ID
```graphql
query {
  getLoansByCustomerId(customerId: 1) {
    id
    loanNumber
    loanType
    principalAmount
    outstandingAmount
    interestRate
    tenureMonths
    monthlyPayment
    status
    nextPaymentDate
  }
}
```

### Query: Get Loans by Status
```graphql
query {
  getLoansByStatus(status: ACTIVE) {
    id
    loanNumber
    loanType
    principalAmount
    outstandingAmount
    status
    customer {
      firstName
      lastName
    }
  }
}
```

### Mutation: Apply for Loan
```graphql
mutation {
  applyForLoan(input: {
    customerId: 2
    loanType: EDUCATION
    principalAmount: 50000.00
    interestRate: 6.0
    tenureMonths: 120
  }) {
    id
    loanNumber
    loanType
    principalAmount
    outstandingAmount
    interestRate
    tenureMonths
    monthlyPayment
    status
  }
}
```

### Mutation: Approve Loan
```graphql
mutation {
  approveLoan(loanId: 3) {
    id
    loanNumber
    loanType
    principalAmount
    status
  }
}
```

### Mutation: Disburse Loan
```graphql
mutation {
  disburseLoan(loanId: 3) {
    id
    loanNumber
    status
    disbursementDate
    maturityDate
    nextPaymentDate
  }
}
```

### Mutation: Make Loan Payment
```graphql
mutation {
  makeLoanPayment(input: {
    loanId: 1
    paymentAmount: 1266.71
  }) {
    id
    loanNumber
    principalAmount
    outstandingAmount
    monthlyPayment
    status
    nextPaymentDate
  }
}
```

## Credit Card Operations

### Query: Get All Credit Cards
```graphql
query {
  getAllCreditCards {
    id
    cardNumber
    cardHolderName
    cardType
    creditLimit
    availableCredit
    outstandingBalance
    minimumPayment
    interestRate
    status
    customer {
      firstName
      lastName
    }
  }
}
```

### Query: Get Credit Cards by Customer ID
```graphql
query {
  getCreditCardsByCustomerId(customerId: 1) {
    id
    cardNumber
    cardType
    creditLimit
    availableCredit
    outstandingBalance
    minimumPayment
    status
    expiryDate
  }
}
```

### Query: Get Credit Cards by Status
```graphql
query {
  getCreditCardsByStatus(status: ACTIVE) {
    id
    cardNumber
    cardHolderName
    cardType
    creditLimit
    availableCredit
    status
  }
}
```

### Mutation: Issue Credit Card
```graphql
mutation {
  issueCreditCard(input: {
    customerId: 2
    cardType: PLATINUM
    creditLimit: 15000.00
  }) {
    id
    cardNumber
    cardHolderName
    cardType
    creditLimit
    availableCredit
    outstandingBalance
    interestRate
    status
    issueDate
    expiryDate
  }
}
```

### Mutation: Block Credit Card
```graphql
mutation {
  blockCreditCard(cardId: 1) {
    id
    cardNumber
    cardType
    status
  }
}
```

### Mutation: Unblock Credit Card
```graphql
mutation {
  unblockCreditCard(cardId: 1) {
    id
    cardNumber
    cardType
    status
  }
}
```

### Mutation: Make Credit Card Payment
```graphql
mutation {
  makeCreditCardPayment(input: {
    cardId: 1
    paymentAmount: 1000.00
  }) {
    id
    cardNumber
    cardType
    creditLimit
    availableCredit
    outstandingBalance
    minimumPayment
  }
}
```

## Complex Scenarios

### Scenario: Complete Customer Onboarding
```graphql
# Step 1: Create Customer
mutation CreateCustomer {
  createCustomer(input: {
    firstName: "Sarah"
    lastName: "Connor"
    email: "sarah.connor@example.com"
    phoneNumber: "+1-555-0200"
    dateOfBirth: "1992-11-15"
    address: "100 Tech Street, San Francisco, CA 94101"
    nationalId: "777-88-9999"
    customerType: PREMIUM
  }) {
    id
    firstName
    lastName
    email
    status
    creditScore
  }
}

# Step 2: Create Checking Account
mutation CreateCheckingAccount {
  createAccount(input: {
    customerId: 4  # Use ID from Step 1
    accountType: CHECKING
    initialBalance: 5000.00
    currency: "USD"
    overdraftLimit: 1000.00
  }) {
    id
    accountNumber
    balance
    status
  }
}

# Step 3: Create Savings Account
mutation CreateSavingsAccount {
  createAccount(input: {
    customerId: 4  # Use ID from Step 1
    accountType: SAVINGS
    initialBalance: 10000.00
    currency: "USD"
  }) {
    id
    accountNumber
    balance
    interestRate
  }
}

# Step 4: Issue Credit Card
mutation IssueCreditCard {
  issueCreditCard(input: {
    customerId: 4  # Use ID from Step 1
    cardType: GOLD
    creditLimit: 8000.00
  }) {
    id
    cardNumber
    cardType
    creditLimit
    availableCredit
  }
}
```

### Scenario: Month-End Banking Operations
```graphql
# Check customer's complete financial position
query FinancialReview {
  getCustomerFinancialSummary(customerId: 1) {
    customer {
      firstName
      lastName
      email
      customerType
      creditScore
    }
    totalAccountBalance
    totalLoanOutstanding
    totalCreditCardOutstanding
    totalAvailableCredit
    netWorth
  }
}

# Process loan payment
mutation LoanPayment {
  makeLoanPayment(input: {
    loanId: 1
    paymentAmount: 1266.71
  }) {
    loanNumber
    outstandingAmount
    nextPaymentDate
  }
}

# Process credit card payment
mutation CardPayment {
  makeCreditCardPayment(input: {
    cardId: 1
    paymentAmount: 500.00
  }) {
    cardNumber
    outstandingBalance
    availableCredit
    minimumPayment
  }
}

# Transfer to savings
mutation MonthlyTransfer {
  transferMoney(
    fromAccountId: 1
    toAccountId: 2
    amount: 1000.00
    description: "Monthly savings transfer"
  ) {
    transactionId
    amount
    status
    beneficiaryName
  }
}
```

### Scenario: Large Purchase with Multiple Accounts
```graphql
# Check available balances
query CheckBalances {
  getAccountsByCustomerId(customerId: 1) {
    accountNumber
    accountType
    balance
    status
  }
}

# Withdraw from savings
mutation WithdrawFromSavings {
  withdrawMoney(
    accountId: 2
    amount: 3000.00
    description: "Transfer to checking for purchase"
  ) {
    transactionId
    amount
    status
  }
}

# Transfer to checking
mutation TransferToChecking {
  transferMoney(
    fromAccountId: 2
    toAccountId: 1
    amount: 3000.00
    description: "Transfer for large purchase"
  ) {
    transactionId
    amount
    status
  }
}

# Make final withdrawal
mutation FinalWithdrawal {
  withdrawMoney(
    accountId: 1
    amount: 5000.00
    description: "Large purchase payment"
  ) {
    transactionId
    amount
    status
  }
}
```
