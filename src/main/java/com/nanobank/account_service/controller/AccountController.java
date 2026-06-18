package com.nanobank.account_service.controller;

import com.nanobank.account_service.domain.Account;
import com.nanobank.account_service.domain.Money;
import com.nanobank.account_service.exception.AccountNotFoundException;
import com.nanobank.account_service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST endpoints for account lifecycle and fund movements.
 * Base path: {@code /api/v1/accounts}
 *
 * <p>Input validation is intentionally thin here — domain rules live in
 * {@link com.nanobank.account_service.domain.Account}, and HTTP error mapping
 * is handled centrally by {@link GlobalExceptionHandler}.
 */
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * POST /api/v1/accounts
     * Creates a new account and returns it with a 201.
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = new Account(request.ownerName, Money.of(request.openingBalance));
        accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    /** GET /api/v1/accounts — returns all accounts, empty list if none exist. */
    @GetMapping
    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }

    /** GET /api/v1/accounts/{id} — 404 if the ID doesn't match any account. */
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable String id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + id));
    }

    /** POST /api/v1/accounts/{id}/deposit — 400 on non-positive amount, 404 on bad ID. */
    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable String id, @RequestBody AmountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + id));
        account.deposit(Money.of(request.amount));
        return accountRepository.save(account);
    }

    /** POST /api/v1/accounts/{id}/withdraw — 400 on insufficient funds or non-positive amount. */
    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable String id, @RequestBody AmountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + id));
        account.withdraw(Money.of(request.amount));
        return accountRepository.save(account);
    }

    /** Request body for account creation. {@code openingBalance} must be positive. */
    public static class CreateAccountRequest {
        public String ownerName;
        public BigDecimal openingBalance;
    }

    /** Shared request body for deposit and withdraw endpoints. */
    public static class AmountRequest {
        public BigDecimal amount;
    }
}
