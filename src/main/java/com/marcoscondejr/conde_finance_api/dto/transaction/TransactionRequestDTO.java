package com.marcoscondejr.conde_finance_api.dto.transaction;

import com.marcoscondejr.conde_finance_api.enums.CategoryType;
import com.marcoscondejr.conde_finance_api.enums.PaymentMethod;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequestDTO(
        @NotNull(message = "A conta é obrigatória")
        Long accountId,

        @NotNull(message = "A categoria é obrigatória")
        Long categoryId,

        @NotNull(message = "A data de transação é obrigatória")
        @PastOrPresent(message = "A data não pode ser maior que a data atual")
        LocalDate transactionDate,

        @Size(max = 100, message = "A descrição precisa conter no máximo 100 caracteres")
        String description,

        @NotNull(message = "O tipo de transação é obrigatório")
        CategoryType transactionType,

        @NotNull(message = "O tipo de pagamento é obrigatório")
        PaymentMethod paymentMethod,

        @NotNull(message = "O valor é obrigatório")
        @DecimalMin(value = "0.00", inclusive = true, message = "O valor não pode ser menor que zero")
        @Digits(integer = 12, fraction = 2, message = "O valor deve ter no máximo 12 dígitos e 2 casas decimais")
        BigDecimal amount
) {
}
