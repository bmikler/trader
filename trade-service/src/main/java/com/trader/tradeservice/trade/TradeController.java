package com.trader.tradeservice.trade;

import com.trader.tradeservice.shared.CryptoCurrency;
import com.trader.tradeservice.user.SecurityUser;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
class TradeController {

    private final TradeService tradeService;
    private final AccountService accountService;

    @GetMapping
    ResponseEntity<AccountInfoDto> getAccountInfo(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(accountService.getAccountInfo(user.getId()));
    }

    @PostMapping
    ResponseEntity<TradeOffer> createOffer(@AuthenticationPrincipal SecurityUser user, @RequestBody TradeOfferRequest tradeOfferRequest) {
        TradeOffer tradeOffer = tradeService.createOffer(new TradeOfferCommand(user.getId(), tradeOfferRequest.currency(), tradeOfferRequest.amount()));
        return new ResponseEntity<>(tradeOffer, HttpStatus.CREATED);
    }

    @PostMapping("/buy")
    void buy(@AuthenticationPrincipal SecurityUser user, @RequestBody TradeRequest tradeRequest) {
        tradeService.buy(new TradeCommand(user.getId(), tradeRequest.tradeOfferId()));
    }

    @PostMapping("/sell")
    void sell(@AuthenticationPrincipal SecurityUser user, @RequestBody TradeRequest tradeRequest) {
        tradeService.sell(new TradeCommand(user.getId(), tradeRequest.tradeOfferId()));
    }
}

record TradeOfferRequest(CryptoCurrency currency, @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount) {}
record TradeRequest(UUID tradeOfferId) {}


