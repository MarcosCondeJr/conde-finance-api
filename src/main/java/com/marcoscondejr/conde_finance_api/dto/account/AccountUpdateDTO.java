package com.marcoscondejr.conde_finance_api.dto.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AccountUpdateDTO(
        Long bankId,

        @Size(max = 200, message = "A descrição tem que ter no máximo 200 caracteres")
        String description,

        @PositiveOrZero(message = "O Saldo inicial não pode ser negativo")
        BigDecimal initialBalance
) {
}
