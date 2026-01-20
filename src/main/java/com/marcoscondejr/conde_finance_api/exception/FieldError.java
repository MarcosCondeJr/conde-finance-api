package com.marcoscondejr.conde_finance_api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldError {
    private String name;
    private String message;

    public FieldError(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
