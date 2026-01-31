package com.marcoscondejr.conde_finance_api.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank(message = "O Login é obrigatório")
        String login,

        @NotBlank(message = "A senha é obrigatório")
        String password
) {
}
