package com.marcoscondejr.conde_finance_api.dto.category;

import com.marcoscondejr.conde_finance_api.enums.CategoryType;
import jakarta.validation.constraints.Size;

public record CategoryUpadateDTO(
        @Size(
                min = 3,
                max = 200,
                message = "O nome da categoria precisa conter entre 3 e 200 caracteres"
        )
        String name,

        CategoryType categoryType
) {
}
