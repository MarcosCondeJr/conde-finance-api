package com.marcoscondejr.conde_finance_api.dto.transaction;

import com.marcoscondejr.conde_finance_api.dto.account.AccountResponseDTO;
import com.marcoscondejr.conde_finance_api.dto.category.CategoryResponseDTO;
import com.marcoscondejr.conde_finance_api.entity.Account;
import com.marcoscondejr.conde_finance_api.entity.Category;
import com.marcoscondejr.conde_finance_api.entity.Transaction;
import com.marcoscondejr.conde_finance_api.enums.CategoryType;
import com.marcoscondejr.conde_finance_api.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponseDTO(
        Long id,

        AccountResponseDTO account,

        CategoryResponseDTO category,

        LocalDate transactionDate,

        String description,

        CategoryType transactionType,

        PaymentMethod paymentMethod,

        BigDecimal amount
) {
    public static TransactionResponseDTO fromEntity(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                AccountResponseDTO.fromEntity(transaction.getAccount()),
                CategoryResponseDTO.fromEntity(transaction.getCategory()),
                transaction.getTransactionDate(),
                transaction.getDescription(),
                transaction.getTransactionType(),
                transaction.getPaymentMethod(),
                transaction.getAmount()
        );
    }
}
