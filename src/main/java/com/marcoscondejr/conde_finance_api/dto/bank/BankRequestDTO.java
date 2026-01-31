package com.marcoscondejr.conde_finance_api.dto.bank;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BankRequestDTO(

    @NotBlank(message = "O Código do banco é obrigatório")
    @Size(min = 3, max = 3, message = "O Código deve conter 3 digitos")
    String code,

    @NotBlank(message = "O nome do banco é obrigatório")
    String name
) {}
