package com.marcoscondejr.conde_finance_api.dto.transaction;

import com.marcoscondejr.conde_finance_api.enums.CategoryType;
import com.marcoscondejr.conde_finance_api.enums.PaymentMethod;

import java.time.LocalDate;

public record TransactionFilter(
        Long categoryId,
        LocalDate startDate,
        LocalDate endDate,
        String description,
        CategoryType transactionType,
        PaymentMethod paymentMethod
) {
}