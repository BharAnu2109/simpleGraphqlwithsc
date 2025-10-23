# API Reference

Complete GraphQL API reference for the Banking Application.

## Base URLs

- GraphQL Endpoint: `http://localhost:8080/graphql`
- GraphiQL UI: `http://localhost:8080/graphiql`
- H2 Console: `http://localhost:8080/h2-console`

## Schema Overview

### Core Types

- **Customer**: Represents a bank customer with personal information
- **Account**: Represents a bank account with balance and transactions
- **Transaction**: Represents a financial transaction
- **Loan**: Represents a loan with payment schedule
- **CreditCard**: Represents a credit card with credit limit

### Enums

#### CustomerStatus
- `ACTIVE`: Customer is active and can perform operations
- `INACTIVE`: Customer is inactive
- `SUSPENDED`: Customer is temporarily suspended
- `BLOCKED`: Customer is blocked from operations

#### CustomerType
- `INDIVIDUAL`: Individual customer
- `BUSINESS`: Business customer
- `PREMIUM`: Premium individual customer
- `VIP`: VIP customer

#### AccountType
- `SAVINGS`: Savings account (3.5% interest)
- `CHECKING`: Checking account (0.5% interest)
- `FIXED_DEPOSIT`: Fixed deposit account (6.5% interest)
- `MONEY_MARKET`: Money market account (4.5% interest)
- `INVESTMENT`: Investment account (5.5% interest)

#### AccountStatus
- `ACTIVE`: Account is active
- `DORMANT`: Account is dormant
- `CLOSED`: Account is closed
- `FROZEN`: Account is frozen

#### TransactionType
- `DEPOSIT`: Money deposited
- `WITHDRAWAL`: Money withdrawn
- `TRANSFER`: Money transferred
- `PAYMENT`: Payment made
- `FEE`: Fee charged
- `INTEREST`: Interest credited
- `REFUND`: Refund processed

#### TransactionStatus
- `PENDING`: Transaction pending
- `COMPLETED`: Transaction completed
- `FAILED`: Transaction failed
- `REVERSED`: Transaction reversed
- `CANCELLED`: Transaction cancelled

#### LoanType
- `PERSONAL`: Personal loan
- `HOME`: Home/Mortgage loan
- `AUTO`: Auto loan
- `EDUCATION`: Education loan
- `BUSINESS`: Business loan

#### LoanStatus
- `PENDING_APPROVAL`: Awaiting approval
- `APPROVED`: Loan approved
- `DISBURSED`: Loan disbursed
- `ACTIVE`: Loan is active
- `PAID_OFF`: Loan fully paid
- `DEFAULTED`: Loan defaulted
- `CLOSED`: Loan closed

#### CardType
- `SILVER`: Silver card (18% interest)
- `GOLD`: Gold card (16% interest)
- `PLATINUM`: Platinum card (14% interest)
- `TITANIUM`: Titanium card (12% interest)
- `SIGNATURE`: Signature card (10% interest)

#### CardStatus
- `ACTIVE`: Card is active
- `BLOCKED`: Card is blocked
- `EXPIRED`: Card is expired
- `CANCELLED`: Card is cancelled
- `SUSPENDED`: Card is suspended

## Query Operations

### Customer Queries

#### getAllCustomers
Get all customers in the system.

**Parameters:** None

**Returns:** `[Customer!]!`

**Example:**
```graphql
query {
  getAllCustomers {
    id firstName lastName email
  }
}
```

#### getCustomerById
Get a customer by ID.

**Parameters:**
- `id: ID!` - Customer ID

**Returns:** `Customer`

#### getCustomerByEmail
Get a customer by email address.

**Parameters:**
- `email: String!` - Customer email

**Returns:** `Customer`

#### getCustomersByStatus
Get customers filtered by status.

**Parameters:**
- `status: CustomerStatus!` - Customer status

**Returns:** `[Customer!]!`

#### getCustomersByType
Get customers filtered by type.

**Parameters:**
- `customerType: CustomerType!` - Customer type

**Returns:** `[Customer!]!`

### Account Queries

#### getAllAccounts
Get all accounts in the system.

**Parameters:** None

**Returns:** `[Account!]!`

#### getAccountById
Get an account by ID.

**Parameters:**
- `id: ID!` - Account ID

**Returns:** `Account`

#### getAccountByNumber
Get an account by account number.

**Parameters:**
- `accountNumber: String!` - Account number

**Returns:** `Account`

#### getAccountsByCustomerId
Get all accounts for a customer.

**Parameters:**
- `customerId: ID!` - Customer ID

**Returns:** `[Account!]!`

#### getAccountsByType
Get accounts filtered by type.

**Parameters:**
- `accountType: AccountType!` - Account type

**Returns:** `[Account!]!`

#### getAccountBalance
Get the current balance of an account.

**Parameters:**
- `accountNumber: String!` - Account number

**Returns:** `Float!`

### Transaction Queries

#### getAllTransactions
Get all transactions in the system.

**Parameters:** None

**Returns:** `[Transaction!]!`

#### getTransactionById
Get a transaction by ID.

**Parameters:**
- `id: ID!` - Transaction ID

**Returns:** `Transaction`

#### getTransactionByTransactionId
Get a transaction by transaction ID.

**Parameters:**
- `transactionId: String!` - Transaction ID

**Returns:** `Transaction`

#### getTransactionsByAccountId
Get all transactions for an account.

**Parameters:**
- `accountId: ID!` - Account ID

**Returns:** `[Transaction!]!`

#### getTransactionsByType
Get transactions filtered by type.

**Parameters:**
- `type: TransactionType!` - Transaction type

**Returns:** `[Transaction!]!`

### Loan Queries

#### getAllLoans
Get all loans in the system.

**Parameters:** None

**Returns:** `[Loan!]!`

#### getLoanById
Get a loan by ID.

**Parameters:**
- `id: ID!` - Loan ID

**Returns:** `Loan`

#### getLoanByNumber
Get a loan by loan number.

**Parameters:**
- `loanNumber: String!` - Loan number

**Returns:** `Loan`

#### getLoansByCustomerId
Get all loans for a customer.

**Parameters:**
- `customerId: ID!` - Customer ID

**Returns:** `[Loan!]!`

#### getLoansByStatus
Get loans filtered by status.

**Parameters:**
- `status: LoanStatus!` - Loan status

**Returns:** `[Loan!]!`

### Credit Card Queries

#### getAllCreditCards
Get all credit cards in the system.

**Parameters:** None

**Returns:** `[CreditCard!]!`

#### getCreditCardById
Get a credit card by ID.

**Parameters:**
- `id: ID!` - Card ID

**Returns:** `CreditCard`

#### getCreditCardByNumber
Get a credit card by card number.

**Parameters:**
- `cardNumber: String!` - Card number

**Returns:** `CreditCard`

#### getCreditCardsByCustomerId
Get all credit cards for a customer.

**Parameters:**
- `customerId: ID!` - Customer ID

**Returns:** `[CreditCard!]!`

#### getCreditCardsByStatus
Get credit cards filtered by status.

**Parameters:**
- `status: CardStatus!` - Card status

**Returns:** `[CreditCard!]!`

### Complex Queries

#### getCustomerFinancialSummary
Get comprehensive financial summary for a customer.

**Parameters:**
- `customerId: ID!` - Customer ID

**Returns:** `CustomerFinancialSummary!`

**Response includes:**
- Customer details
- Total account balance
- Total loan outstanding
- Total credit card outstanding
- Total available credit
- Net worth (assets - liabilities)

## Mutation Operations

### Customer Mutations

#### createCustomer
Create a new customer.

**Input:**
```graphql
input CreateCustomerInput {
  firstName: String!
  lastName: String!
  email: String!
  phoneNumber: String!
  dateOfBirth: String
  address: String
  nationalId: String
  customerType: CustomerType!
}
```

**Returns:** `Customer!`

**Default values:**
- Status: `ACTIVE`
- Credit Score: `700.0`

#### updateCustomer
Update an existing customer.

**Input:**
```graphql
input UpdateCustomerInput {
  id: ID!
  firstName: String
  lastName: String
  phoneNumber: String
  address: String
  status: CustomerStatus
  creditScore: Float
}
```

**Returns:** `Customer!`

#### deleteCustomer
Delete a customer.

**Parameters:**
- `id: ID!` - Customer ID

**Returns:** `Boolean!`

#### suspendCustomer
Suspend a customer account.

**Parameters:**
- `id: ID!` - Customer ID

**Returns:** `Customer!`

#### activateCustomer
Activate a customer account.

**Parameters:**
- `id: ID!` - Customer ID

**Returns:** `Customer!`

### Account Mutations

#### createAccount
Create a new bank account.

**Input:**
```graphql
input CreateAccountInput {
  customerId: ID!
  accountType: AccountType!
  initialBalance: Float!
  currency: String!
  overdraftLimit: Float
}
```

**Returns:** `Account!`

**Notes:**
- Account number is auto-generated
- Interest rate is set based on account type
- Status is set to `ACTIVE`

#### closeAccount
Close an account.

**Parameters:**
- `id: ID!` - Account ID

**Returns:** `Account!`

#### freezeAccount
Freeze an account (blocks transactions).

**Parameters:**
- `id: ID!` - Account ID

**Returns:** `Account!`

#### unfreezeAccount
Unfreeze an account.

**Parameters:**
- `id: ID!` - Account ID

**Returns:** `Account!`

### Transaction Mutations

#### createTransaction
Create a generic transaction.

**Input:**
```graphql
input CreateTransactionInput {
  accountId: ID!
  type: TransactionType!
  amount: Float!
  currency: String!
  description: String
  beneficiaryName: String
  beneficiaryAccount: String
  destinationAccountId: ID
}
```

**Returns:** `Transaction!`

#### depositMoney
Deposit money into an account.

**Parameters:**
- `accountId: ID!` - Account ID
- `amount: Float!` - Deposit amount
- `description: String` - Description

**Returns:** `Transaction!`

**Effects:**
- Increases account balance
- Creates DEPOSIT transaction
- Updates last transaction date

#### withdrawMoney
Withdraw money from an account.

**Parameters:**
- `accountId: ID!` - Account ID
- `amount: Float!` - Withdrawal amount
- `description: String` - Description

**Returns:** `Transaction!`

**Validations:**
- Checks sufficient balance
- Account must be active

**Effects:**
- Decreases account balance
- Creates WITHDRAWAL transaction
- Updates last transaction date

#### transferMoney
Transfer money between accounts.

**Parameters:**
- `fromAccountId: ID!` - Source account ID
- `toAccountId: ID!` - Destination account ID
- `amount: Float!` - Transfer amount
- `description: String` - Description

**Returns:** `Transaction!`

**Validations:**
- Checks sufficient balance in source account
- Both accounts must be active

**Effects:**
- Decreases source account balance
- Increases destination account balance
- Creates TRANSFER transaction
- Updates last transaction date for both accounts

### Loan Mutations

#### applyForLoan
Apply for a new loan.

**Input:**
```graphql
input CreateLoanInput {
  customerId: ID!
  loanType: LoanType!
  principalAmount: Float!
  interestRate: Float!
  tenureMonths: Int!
}
```

**Returns:** `Loan!`

**Notes:**
- Loan number is auto-generated
- Monthly payment is calculated automatically using amortization formula
- Initial status is `PENDING_APPROVAL`
- Outstanding amount equals principal amount

#### approveLoan
Approve a pending loan.

**Parameters:**
- `loanId: ID!` - Loan ID

**Returns:** `Loan!`

**Effects:**
- Changes status to `APPROVED`

#### disburseLoan
Disburse an approved loan.

**Parameters:**
- `loanId: ID!` - Loan ID

**Returns:** `Loan!`

**Validations:**
- Loan must be in `APPROVED` status

**Effects:**
- Changes status to `DISBURSED`
- Sets disbursement date to current date
- Calculates maturity date (disbursement + tenure)
- Sets next payment date to one month from disbursement

#### makeLoanPayment
Make a payment toward a loan.

**Input:**
```graphql
input LoanPaymentInput {
  loanId: ID!
  paymentAmount: Float!
}
```

**Returns:** `Loan!`

**Effects:**
- Reduces outstanding amount
- Updates next payment date
- Sets status to `PAID_OFF` if outstanding becomes zero or less
- Otherwise sets status to `ACTIVE`

### Credit Card Mutations

#### issueCreditCard
Issue a new credit card.

**Input:**
```graphql
input CreateCreditCardInput {
  customerId: ID!
  cardType: CardType!
  creditLimit: Float!
}
```

**Returns:** `CreditCard!`

**Default values:**
- Card number: Auto-generated (16 digits)
- Available credit: Equals credit limit
- Outstanding balance: 0
- Minimum payment: 0
- Status: `ACTIVE`
- Interest rate: Based on card type
- Issue date: Current date
- Expiry date: 5 years from issue
- CVV: Auto-generated (3 digits)
- Billing date: 1st of current month
- Payment due date: 15th of current month

#### blockCreditCard
Block a credit card.

**Parameters:**
- `cardId: ID!` - Card ID

**Returns:** `CreditCard!`

#### unblockCreditCard
Unblock a credit card.

**Parameters:**
- `cardId: ID!` - Card ID

**Returns:** `CreditCard!`

#### makeCreditCardPayment
Make a payment toward credit card balance.

**Input:**
```graphql
input CreditCardPaymentInput {
  cardId: ID!
  paymentAmount: Float!
}
```

**Returns:** `CreditCard!`

**Effects:**
- Reduces outstanding balance
- Increases available credit
- Recalculates minimum payment (5% of outstanding)

## Error Handling

The API returns GraphQL errors in the following format:

```json
{
  "errors": [
    {
      "message": "Error message",
      "locations": [{"line": 1, "column": 1}],
      "path": ["fieldName"]
    }
  ]
}
```

### Common Errors

- **Customer not found**: When customer ID doesn't exist
- **Account not found**: When account ID doesn't exist
- **Insufficient balance**: When withdrawal/transfer amount exceeds balance
- **Loan must be approved before disbursement**: When trying to disburse non-approved loan
- **Transaction validation errors**: Various validation errors during transactions

## Rate Limiting

No rate limiting is currently implemented. Consider adding rate limiting for production use.

## Authentication

No authentication is currently implemented. This is a demonstration application. For production use, implement proper authentication and authorization.

## Versioning

Current version: 1.0.0

The GraphQL schema is versioned through the API itself. Breaking changes should be handled by deprecating fields and adding new ones.

## Support

For issues and questions, please refer to the README.md and EXAMPLES.md files.
