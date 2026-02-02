package com.marcoscondejr.conde_finance_api.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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

    @ExceptionHandler(BankAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBankAlreadyExists(
            BankAlreadyExistsException ex
    ) {
        return this.buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAccountAlreadyExists(
            AccountAlreadyExistsException ex
    ) {
        return this.buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleObjectNotFound(
            ObjectNotFoundException ex
    ) {
        return this.buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(
            DataIntegrityViolationException ex
    ) {
        String message = "Operação não permitida: registro está em uso";

        if (ex.getCause() instanceof ConstraintViolationException) {
            String rootMsg = ex.getRootCause().getMessage();

            if (rootMsg.contains("fk_account_bank_id")) {
                message = "Não é possível excluir o banco, pois existe contas vinculadas";
            }
        }

        return this.buildError(HttpStatus.CONFLICT, message);
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(status.value());
        error.setTimestamp(OffsetDateTime.now());
        error.setTitle(message);

        return ResponseEntity.status(status).body(error);
    }
}
