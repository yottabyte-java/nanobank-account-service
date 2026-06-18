package com.nanobank.account_service.domain;

import com.nanobank.account_service.exception.InsufficientFundsException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

/**
 * Core domain entity — owns the balance and enforces the rules around moving money.
 *
 * <p>Balance is stored as a plain {@link BigDecimal} column so JPA doesn't need to
 * know about {@link Money}. It's wrapped back into {@code Money} on the way out via
 * {@link #getBalance()}, keeping the value-object contract intact at the domain boundary.
 */
@Entity
public class Account {

    @Id
    private String accountId;

    private String ownerName;
    private BigDecimal balance;

    protected Account() {
        // required by JPA, never called directly in your own code
    }

    /**
     * @param ownerName      display name — no uniqueness constraint, purely informational
     * @param openingBalance must satisfy {@link Money} invariants (non-null, non-negative)
     */
    public Account(String ownerName, Money openingBalance) {
        this.accountId = AccountId.generate().getValue().toString(); // auto-generate a unique ID
        this.ownerName = ownerName;
        this.balance = openingBalance.getAmount(); // store as BigDecimal, not Money (JPA doesn't know Money)
    }

    /**
     * @param amount must be positive; throws {@link IllegalArgumentException} otherwise
     */
    public void deposit(Money amount) {
        // can't deposit zero or negative, that makes no sense
        if (amount.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        // wrap current balance in Money so we can use the add() method
        Money updated = Money.of(this.balance).add(amount);
        this.balance = updated.getAmount(); // save back as BigDecimal
    }

    /**
     * Debits the balance. Fails fast on two conditions: non-positive amount, or
     * amount exceeding current balance.
     *
     * @param amount the amount to deduct — must be positive and ≤ current balance
     * @throws IllegalArgumentException    if {@code amount} isn't positive
     * @throws InsufficientFundsException  if the balance would go negative
     */
    public void withdraw(Money amount) {
        // same check as deposit — you can't withdraw nothing or go below zero
        if (amount.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        Money current = Money.of(this.balance);
        // stop here if they're trying to take out more than they have
        if (current.isLessThan(amount)) {
            throw new InsufficientFundsException(
                "Cannot withdraw " + amount.getAmount() + " — balance is " + this.balance);
        }
        this.balance = current.subtract(amount).getAmount();
    }

    public String getAccountId() { return accountId; }
    public String getOwnerName() { return ownerName; }
    /** Wraps the stored {@link BigDecimal} in a {@link Money} — never returns {@code null}. */
    public Money getBalance() { return Money.of(this.balance); }
}