package com.banking.graphql.resolver;

import com.banking.graphql.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFinancialSummary {
    private Customer customer;
    private Double totalAccountBalance;
    private Double totalLoanOutstanding;
    private Double totalCreditCardOutstanding;
    private Double totalAvailableCredit;
    private Double netWorth;
}
