package com.marcoscondejr.conde_finance_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ErrorResponse> handleBadCredentials(Exception ex) {
        return this.buildError(HttpStatus.UNAUTHORIZED, "Login ou senha inválidos.");
    }

    @ExceptionHandler({TokenCreationException.class, InvalidTokenException.class})
    public ResponseEntity<ErrorResponse> handleTokenErrors(RuntimeException ex) {
        return this.buildError(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserExists(UserAlreadyExistsException ex) {
        return this.buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(status.value());
        error.setTimestamp(OffsetDateTime.now());
        error.setTitle(message);

        return ResponseEntity.status(status).body(error);
    }
}
