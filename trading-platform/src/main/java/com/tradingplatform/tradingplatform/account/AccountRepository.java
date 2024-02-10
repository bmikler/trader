package com.tradingplatform.tradingplatform.account;

import java.util.Optional;
import java.util.UUID;

interface AccountRepository {

    Optional<Account> getAccountByUserId(UUID userId);

    void save(Account account);

}
