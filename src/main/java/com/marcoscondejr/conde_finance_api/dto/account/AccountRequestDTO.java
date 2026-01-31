package com.marcoscondejr.conde_finance_api.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record AccountRequestDTO(
        @NotBlank(message = "O Banco é obrigatório")
        Long bankId,

        @Size(max = 200, message = "A descrição tem que ter no máximo 200 caracteres")
        String description,

        @NotBlank(message = "É necessário informar o saldo inicial da conta")
        @PositiveOrZero(message = "O Saldo inicial não pode ser negativo")
        double initialBalance
) {
}
