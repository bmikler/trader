package com.tradingplatform.tradingplatform.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class AccountService {

    private final AccountRepository accountRepository;
    private final RateRepository rateRepository;

    AccountInfoDto getAccountInfo(UUID userId) {
        Map<CryptoCurrency, BigDecimal> rateTable = rateRepository.getRateTable();
        Account account = getAccountByUserIdOrThrow(userId);
        return new AccountInfoDto(account.getMoney(), account.getAssets(), account.calculateTotal(rateTable));
    }

    void buy(TradeCommand command) {
        Account account = getAccountByUserIdOrThrow(command.userId());
        BigDecimal price = getRateOrThrow(command.currency());
        account.buy(command.currency(), command.amount(), price);
        accountRepository.save(account);
    }

    void sell(TradeCommand command) {
        Account account = getAccountByUserIdOrThrow(command.userId());
        BigDecimal price = getRateOrThrow(command.currency());
        account.sell(command.currency(), command.amount(), price);
        accountRepository.save(account);
    }

    private Account getAccountByUserIdOrThrow(UUID userId) {
        return accountRepository.getAccountByUserId(userId).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    private BigDecimal getRateOrThrow(CryptoCurrency currency) {
        return rateRepository.getPrice(currency).orElseThrow(() -> new RuntimeException("Rate not found"));
    }

}

record TradeCommand(UUID userId, CryptoCurrency currency, BigDecimal amount) {};