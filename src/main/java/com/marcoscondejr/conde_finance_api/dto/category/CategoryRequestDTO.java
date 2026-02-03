package com.marcoscondejr.conde_finance_api.dto.category;

import com.marcoscondejr.conde_finance_api.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        @NotBlank(message = "O nome da categoria é obrigatório")
        @Size(
                min = 3,
                max = 200,
                message = "O nome da categoria precisa conter entre 3 e 200 caracteres"
        )
        String name,

        @NotNull(message = "A tipo de categoria é obrigatório")
        CategoryType categoryType
) {
}
