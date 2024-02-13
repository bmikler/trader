package com.tradingplatform.tradingplatform.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> getAccountByUserId(UUID userId);

}
