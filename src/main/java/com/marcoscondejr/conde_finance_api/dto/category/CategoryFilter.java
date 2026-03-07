package com.marcoscondejr.conde_finance_api.dto.category;

import com.marcoscondejr.conde_finance_api.enums.CategoryType;

public record CategoryFilter (
        String name,
        CategoryType categoryType
){
}
