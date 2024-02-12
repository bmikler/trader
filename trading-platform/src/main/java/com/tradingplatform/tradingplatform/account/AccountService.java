package com.tradingplatform.tradingplatform.account;


import com.tradingplatform.tradingplatform.rate.CryptoCurrency;
import com.tradingplatform.tradingplatform.rate.Rate;
import com.tradingplatform.tradingplatform.rate.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class AccountService {

    private final AccountRepository accountRepository;
    private final RateService rateService;

    AccountInfoDto getAccountInfo(UUID userId) {
        List<Rate> rateTable = rateService.getRateTable();
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
        return rateService.getPrice(currency).orElseThrow(() -> new RuntimeException("Rate not found"));
    }

}

record TradeCommand(UUID userId, CryptoCurrency currency, BigDecimal amount) {};