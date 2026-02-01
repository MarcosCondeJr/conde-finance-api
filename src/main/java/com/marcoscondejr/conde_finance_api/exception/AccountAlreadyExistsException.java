package com.marcoscondejr.conde_finance_api.exception;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
