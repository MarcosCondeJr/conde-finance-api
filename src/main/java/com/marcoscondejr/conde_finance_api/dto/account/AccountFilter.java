package com.marcoscondejr.conde_finance_api.dto.account;

public record AccountFilter(
        Long bankId,
        String description,
        Boolean active
) {
}
