package com.marcoscondejr.conde_finance_api.exception;

public class BankAlreadyExistsException extends RuntimeException {
    public BankAlreadyExistsException(String message) {
        super(message);
    }
}