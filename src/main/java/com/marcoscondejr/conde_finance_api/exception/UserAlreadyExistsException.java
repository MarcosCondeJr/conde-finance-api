package com.marcoscondejr.conde_finance_api.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Este login já está em uso.");
    }
}
