package com.nanobank.account_service.controller;

import com.nanobank.account_service.exception.AccountNotFoundException;
import com.nanobank.account_service.exception.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Catches domain exceptions thrown anywhere in the controller layer and maps
 * them to the appropriate HTTP status codes.
 *
 * <p>Add a new {@code @ExceptionHandler} here whenever you introduce a new domain
 * exception — don't let unknown exceptions fall through to a generic 500.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 404 — account ID didn't match anything in the DB. */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleNotFound(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /** 400 — debit request exceeded the available balance. */
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFunds(InsufficientFundsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /** 400 — catches validation failures thrown directly from domain methods. */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
