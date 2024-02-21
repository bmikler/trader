package com.tradingplatform.tradingplatform.trade;

import com.tradingplatform.tradingplatform.shared.CryptoCurrency;
import com.tradingplatform.tradingplatform.user.SecurityUser;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
class AccountController {

    private final TradeService tradeService;
    private final AccountService accountService;

    @GetMapping
    ResponseEntity<AccountInfoDto> getAccountInfo(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(accountService.getAccountInfo(user.getId()));
    }


    @PostMapping("/offer")
    ResponseEntity<TradeOffer> getOffer(@AuthenticationPrincipal SecurityUser user, @RequestBody TradeRequest tradeRequest) {
        TradeOffer tradeOffer = tradeService.createOffer(new TradeOfferCommand(user.getId(), tradeRequest.currency(), tradeRequest.amount()));
        return ResponseEntity.ok(tradeOffer);
    }

    @PostMapping("/buy")
    void buy(@AuthenticationPrincipal SecurityUser user, @RequestBody UUID tradeOfferId) {
        tradeService.buy(new TradeCommand(user.getId(), tradeOfferId));
    }

    @PostMapping("/sell")
    void sell(@AuthenticationPrincipal SecurityUser user, @RequestBody UUID tradeOfferId) {
        tradeService.sell(new TradeCommand(user.getId(), tradeOfferId));
    }
}

record TradeRequest(CryptoCurrency currency, @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount) {}


