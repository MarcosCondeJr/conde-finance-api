package com.marcoscondejr.conde_finance_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BankRequestDTO(

    @NotNull(message = "O Código do banco é obrigatório")
    @Size(min = 3, max = 3, message = "O Código deve conter 3 digitos")
    String code,

    @NotNull(message = "O nome do banco é obrigatório")
    String name
) {}
