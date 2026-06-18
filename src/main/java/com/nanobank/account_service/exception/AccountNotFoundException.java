package com.nanobank.account_service.exception;

/**
 * Thrown when a lookup by account ID comes back empty.
 *
 * <p>Maps to a 404 in the controller layer. Using a dedicated exception here
 * (rather than returning {@code null} or {@code Optional.empty()}) keeps the
 * service layer clean and makes the failure path explicit at the call site.
 */
public class AccountNotFoundException extends RuntimeException {

    /**
     * @param message include the ID that was searched so the log line pinpoints
     *                the missing record immediately (e.g. {@code "No account found with ID: 99"})
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}
