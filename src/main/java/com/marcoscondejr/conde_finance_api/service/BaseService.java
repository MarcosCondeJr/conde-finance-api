package com.marcoscondejr.conde_finance_api.service;

import com.marcoscondejr.conde_finance_api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {

    @Autowired
    protected CurrentUserService currentUserService;

    protected User getCurrentUser() {
        return this.currentUserService.getCurrentUser();
    }

    protected Long getCurrentUserId() {
        return this.currentUserService.getCurrentUserId();
    }
}
