package com.marcoscondejr.conde_finance_api.dto;

import com.marcoscondejr.conde_finance_api.enums.UserRole;

public record UserResponseDTO(
        String name,
        String email,
        String login,
        UserRole role
) {
}
