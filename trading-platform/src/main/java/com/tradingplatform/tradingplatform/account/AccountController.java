package com.tradingplatform.tradingplatform.account;

import com.tradingplatform.tradingplatform.rate.CryptoCurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
class AccountController {

    private final AccountService tradeService;

    @GetMapping
    ResponseEntity<AccountInfoDto> getAccountInfo(@RequestParam UUID userId) {
        return ResponseEntity.ok(tradeService.getAccountInfo(userId));
    }

    @PostMapping("/buy")
    void buy(@RequestParam UUID userId, @RequestBody TradeRequest tradeRequest) {
        tradeService.buy(new TradeCommand(userId, tradeRequest.currency(), tradeRequest.amount()));
    }

    @PostMapping("/sell")
    void sell(@RequestParam UUID userId, @RequestBody TradeRequest tradeRequest) {
        tradeService.sell(new TradeCommand(userId, tradeRequest.currency(), tradeRequest.amount()));
    }
}

record TradeRequest(CryptoCurrency currency, BigDecimal amount) {}

record AccountInfoDto(BigDecimal money, Map<CryptoCurrency, BigDecimal> assets, BigDecimal summary) {}

