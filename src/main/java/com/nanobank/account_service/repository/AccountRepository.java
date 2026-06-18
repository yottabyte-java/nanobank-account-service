package com.nanobank.account_service.repository;

import com.nanobank.account_service.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Persistence layer for {@link com.nanobank.account_service.domain.Account}.
 *
 * <p>Spring Data generates all standard CRUD implementations at runtime —
 * no SQL or boilerplate needed here. Add method signatures (e.g.
 * {@code findByOwnerName}) only when you need queries beyond what JpaRepository
 * already provides.
 */
public interface AccountRepository extends JpaRepository<Account, String> {
}
