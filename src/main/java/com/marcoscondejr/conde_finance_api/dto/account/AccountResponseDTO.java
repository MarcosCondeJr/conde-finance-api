package com.marcoscondejr.conde_finance_api.dto.account;

import com.marcoscondejr.conde_finance_api.dto.bank.BankResponseDTO;

import java.math.BigDecimal;

public record AccountResponseDTO(
        Long id,
        BankResponseDTO bank,
        String description,
        BigDecimal initialBalance,
        BigDecimal balance,
        boolean active
) {
}
