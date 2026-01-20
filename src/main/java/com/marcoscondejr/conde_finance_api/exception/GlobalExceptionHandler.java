package com.marcoscondejr.conde_finance_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {
        List<FieldError> fieldsError = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new FieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ErrorResponse error = new ErrorResponse();
        error.setTitle("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.");
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(OffsetDateTime.now());
        error.setFields(fieldsError);

        return ResponseEntity.badRequest().body(error);
    }
}
