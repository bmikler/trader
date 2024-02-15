package com.tradingplatform.tradingplatform.trade;

import com.tradingplatform.tradingplatform.rate.CryptoCurrency;
import com.tradingplatform.tradingplatform.rate.Rate;
import com.tradingplatform.tradingplatform.rate.RateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class AccountService {

    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;
    private final RateService rateService;

    void createAccount(UUID userId) {
        Account account = accountFactory.createAccount(userId);
        accountRepository.save(account);
    }

    AccountInfoDto getAccountInfo(UUID userId) {
        Account account = accountRepository.getAccountByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Account not found"));
        Map<CryptoCurrency, BigDecimal> rates = rateService.getRateTable().stream().collect(Collectors.toMap(Rate::currency, Rate::value));
        return new AccountInfoDto(userId, account.getMoney(), account.getAssets(), account.getTotal(rates));
    }
}

record AccountInfoDto(UUID userId, BigDecimal balance, Map<CryptoCurrency, BigDecimal> assets, BigDecimal total) {};
