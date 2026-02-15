package com.marcoscondejr.conde_finance_api.dto.account;

import com.marcoscondejr.conde_finance_api.entity.Account;
import com.marcoscondejr.conde_finance_api.entity.Bank;

import java.math.BigDecimal;

public record AccountResponseDTO(
        Long id,
        Bank bank,
        String description,
        BigDecimal initialBalance,
        BigDecimal balance,
        boolean active
) {
    public static AccountResponseDTO fromEntity(Account accountd) {
        return new AccountResponseDTO(
                accountd.getId(),
                accountd.getBank(),
                accountd.getDescription(),
                accountd.getInitialBalance(),
                accountd.getBalance(),
                accountd.isActive()
        );
    }
}
