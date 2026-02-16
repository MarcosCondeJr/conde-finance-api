package com.marcoscondejr.conde_finance_api.dto.bank;

public record BankResponseDTO(
        Long id,
        String name,
        String code,
        boolean active
) {}