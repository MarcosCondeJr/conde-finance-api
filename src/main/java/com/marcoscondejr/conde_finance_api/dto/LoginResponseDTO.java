package com.marcoscondejr.conde_finance_api.dto;

public record LoginResponseDTO(
        UserResponseDTO user,
        String token
) {
}
