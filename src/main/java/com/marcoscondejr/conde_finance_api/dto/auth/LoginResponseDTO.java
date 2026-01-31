package com.marcoscondejr.conde_finance_api.dto.auth;

import com.marcoscondejr.conde_finance_api.dto.user.UserResponseDTO;

public record LoginResponseDTO(
        UserResponseDTO user,
        String token
) {
}
