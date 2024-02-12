package com.tradingplatform.tradingplatform.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> getAccountByUserId(UUID userId);

}
