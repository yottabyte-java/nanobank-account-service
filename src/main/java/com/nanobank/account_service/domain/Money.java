package com.nanobank.account_service.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Immutable monetary value object — no currency field, single-currency service.
 *
 * <p>Always go through the static factories; the constructor is private.
 * Every arithmetic operation returns a new instance, nothing mutates in place.
 */
public final class Money {

    private final BigDecimal amount;

    private Money(BigDecimal amount) {
        Objects.requireNonNull(amount, "Money amount must not be null");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative: " + amount);
        }
        this.amount = amount;
    }

    /** @param amount must be non-negative */
    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    /**
     * Convenience factory for string literals and JSON-deserialized values.
     * Prefer this over passing a {@code double} — avoids floating-point drift.
     *
     * @param amount decimal string, e.g. {@code "100.00"}
     */
    public static Money of(String amount) {
        return new Money(new BigDecimal(amount));
    }

    /** Starting point for accumulators and default balances. */
    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    /** Returns a new {@code Money} with the combined amount. */
    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    /**
     * Returns a new {@code Money} with {@code other} deducted. Does not guard
     * against going negative — that's the caller's responsibility (see
     * {@link com.nanobank.account_service.domain.Account#withdraw}).
     */
    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }

    public boolean isGreaterThan(Money other) {
        return this.amount.compareTo(other.amount) > 0;
    }

    public boolean isLessThan(Money other) {
        return this.amount.compareTo(other.amount) < 0;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Two {@code Money} values are equal if their amounts are numerically equal —
     * {@code 1.0} and {@code 1.00} are the same. Uses {@code compareTo}, not
     * {@code BigDecimal.equals}, intentionally.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money other)) return false;
        return amount.compareTo(other.amount) == 0;
    }

    /** Consistent with equals — strips trailing zeros so {@code 1.0} and {@code 1.00} hash the same. */
    @Override
    public int hashCode() {
        return amount.stripTrailingZeros().hashCode();
    }

    @Override
    public String toString() {
        return amount.toPlainString();
    }
}
