package com.trader.tradeservice.trade;

import com.trader.tradeservice.shared.CryptoCurrency;
import com.trader.tradeservice.rate.Rate;
import com.trader.tradeservice.rate.RateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
class AccountService {

    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;
    private final RateService rateService;

//    @EventListener
//    public void createAccount(RegisterUserEvent event) {
//        Account account = accountFactory.createAccount(event.getUserId());
//        accountRepository.save(account);
//        log.info("Account for user with id {} has been created", event.getUserId());
//    }

    AccountInfoDto getAccountInfo(UUID userId) {
        Account account = accountRepository.getAccountByUserId(userId).orElseThrow(() -> new EntityNotFoundException("Account not found"));
        Map<CryptoCurrency, BigDecimal> rates = rateService.getRateTable().stream().collect(Collectors.toMap(Rate::currency, Rate::value));
        return new AccountInfoDto(account.getMoney(), account.getAssets(), account.getTotal(rates));
    }
}

record AccountInfoDto(BigDecimal balance, Map<CryptoCurrency, BigDecimal> assets, BigDecimal total) {};
