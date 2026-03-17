package com.marcoscondejr.conde_finance_api.dto.bank;

import lombok.Getter;
import lombok.Setter;

public record BankFilter(
        String code,

        String name,

        Boolean active
) {
}
