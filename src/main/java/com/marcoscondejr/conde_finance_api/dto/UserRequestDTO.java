package com.marcoscondejr.conde_finance_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "O Nome é obrigatório")
        String name,

        @NotBlank(message = "O Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Pattern(
                regexp = "\\d{11}",
                message = "CPF deve conter 11 dígitos"
        )
        String cpf,

        @NotBlank(message = "A Senha é obrigatória")
        @Size(min = 8, message = "A Senha deve ter no mínimo 8 caracteres")
        String password
) {
}
