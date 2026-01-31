package com.marcoscondejr.conde_finance_api.dto.user;

import com.marcoscondejr.conde_finance_api.entity.User;
import com.marcoscondejr.conde_finance_api.enums.UserRole;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String login,
        UserRole role
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getRole()
        );
    }
}
