package com.marcoscondejr.conde_finance_api.dto.transaction;

import com.marcoscondejr.conde_finance_api.enums.CategoryType;
import com.marcoscondejr.conde_finance_api.enums.PaymentMethod;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionUpdateDTO(
        Long accountId,

        Long categoryId,

        @PastOrPresent(message = "A data não pode ser maior que a data atual")
        LocalDate transactionDate,

        @Size(max = 100, message = "A descrição precisa conter no máximo 100 caracteres")
        String description,

        CategoryType transactionType,

        PaymentMethod paymentMethod,

        @DecimalMin(value = "0.00", inclusive = true, message = "O valor não pode ser menor que zero")
        @Digits(integer = 12, fraction = 2, message = "O valor deve ter no máximo 12 dígitos e 2 casas decimais")
        BigDecimal amount
) {
}
