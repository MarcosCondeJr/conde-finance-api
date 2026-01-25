package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserService {
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object object = auth.getPrincipal();

        if (object instanceof User user) {
            return user;
        }
        return null;
    }

    public Long getCurrentUserId() {
        User user = getCurrentUser();

        return user != null ? user.getId() : null;
    }
}
