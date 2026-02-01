package com.marcoscondejr.conde_finance_api.dto.account;

import com.marcoscondejr.conde_finance_api.entity.Account;

import java.math.BigDecimal;

public record AccountResponseDTO(
        Long id,
        Long bankId,
        String description,
        BigDecimal initialBalance,
        boolean active
) {
    public static AccountResponseDTO fromEntity(Account accountd) {
        return new AccountResponseDTO(
                accountd.getId(),
                accountd.getBankId(),
                accountd.getDescription(),
                accountd.getInitialBalance(),
                accountd.isActive()
        );
    }
}
