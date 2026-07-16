package com.marcoscondejr.conde_finance_api.dto.bank;

public record BankFilter(
        String code,

        String name,

        Boolean active
) {
}
