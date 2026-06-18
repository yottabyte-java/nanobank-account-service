package com.nanobank.account_service.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Typed wrapper around a UUID — prevents account IDs from being passed around as
 * raw strings and accidentally mixed up with other identifier types.
 */
public final class AccountId {

    private final UUID value;

    private AccountId(UUID value) {
        this.value = Objects.requireNonNull(value, "AccountId value must not be null");
    }

    /** Use when you already have a UUID from storage or an incoming request. */
    public static AccountId of(UUID value) {
        return new AccountId(value);
    }

    /** Mints a fresh ID — called once per account at creation time. */
    public static AccountId generate() {
        return new AccountId(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountId other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
