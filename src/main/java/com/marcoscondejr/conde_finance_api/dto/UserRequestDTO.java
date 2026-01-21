package com.marcoscondejr.conde_finance_api.dto;

import com.marcoscondejr.conde_finance_api.enums.UserRole;
import jakarta.validation.constraints.*;

public record UserRequestDTO(
        @NotBlank(message = "O Nome é obrigatório")
        String name,

        @NotBlank(message = "O Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Pattern(
                regexp = "\\d{11}",
                message = "O Login deve conter 11 dígitos"
        )
        String login,

        @NotBlank(message = "A Senha é obrigatória")
        @Size(min = 8, message = "A Senha deve ter no mínimo 8 caracteres")
        String password,

        @NotNull(message = "A função do usuário é obrigatório")
        UserRole role
) {
}
