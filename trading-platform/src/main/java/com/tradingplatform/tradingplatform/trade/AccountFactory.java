package com.tradingplatform.tradingplatform.trade;



import com.tradingplatform.tradingplatform.shared.CryptoCurrency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
class AccountFactory {

    private final BigDecimal startingBalance;

    AccountFactory(@Value("${starting-balance}") BigDecimal startingBalance) {
        this.startingBalance = startingBalance;
    }

    Account createAccount(UUID userId) {
        Map<CryptoCurrency, BigDecimal> assets = Arrays.stream(CryptoCurrency.values())
                .collect(Collectors.toMap(currency -> currency, value -> BigDecimal.ZERO));
        return new Account(userId, startingBalance, assets);
    }

}
