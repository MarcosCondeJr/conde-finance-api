package com.marcoscondejr.conde_finance_api.dto.category;

import com.marcoscondejr.conde_finance_api.entity.Category;
import com.marcoscondejr.conde_finance_api.enums.CategoryType;

public record CategoryResponseDTO(
        Long id,
        String name,
        CategoryType categoryType
) {
    public static CategoryResponseDTO fromEntity(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getCategoryType()
        );
    }
}
