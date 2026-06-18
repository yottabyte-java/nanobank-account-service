package com.nanobank.account_service.exception;

/**
 * Thrown when a debit or transfer request exceeds the account's available balance.
 *
 * <p>Keeping this as an unchecked exception means callers aren't forced to wrap
 * every service call in a try/catch — the {@code @ExceptionHandler} in the
 * controller layer catches it and maps it to a 400 response instead.
 */
public class InsufficientFundsException extends RuntimeException {

    /**
     * @param message should include the account ID and the shortfall amount so the
     *                log line is actually useful when debugging (e.g.
     *                {@code "Account 42 has £30.00 but £75.00 was requested"})
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
