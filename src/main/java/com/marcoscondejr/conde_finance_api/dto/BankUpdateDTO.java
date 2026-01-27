package com.marcoscondejr.conde_finance_api.dto;

import jakarta.validation.constraints.Size;

public record BankUpdateDTO(
        @Size(min = 3, max = 3, message = "O Código deve conter 3 digitos")
        String code,

        String name
) {
}
