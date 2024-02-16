package com.tradingplatform.tradingplatform.trade;

import com.tradingplatform.tradingplatform.rate.CryptoCurrency;
import com.tradingplatform.tradingplatform.user.SecurityUser;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    String test(@AuthenticationPrincipal SecurityUser principal) {
        System.err.println(principal.getId() + " " + principal.getUsername() + " " + principal.getAuthorities());
        return "TradeController works";
    }

    @PostMapping("/offer")
    ResponseEntity<TradeOffer> getOffer(@RequestParam UUID userId, @RequestBody TradeRequest tradeRequest) {
        TradeOffer tradeOffer = tradeService.createOffer(new TradeOfferCommand(userId, tradeRequest.currency(), tradeRequest.amount()));
        return ResponseEntity.ok(tradeOffer);
    }

    @PostMapping("/buy")
    void buy(@RequestParam UUID userId, @RequestBody UUID tradeOfferId) {
        tradeService.buy(new TradeCommand(userId, tradeOfferId));
    }

    @PostMapping("/sell")
    void sell(@RequestParam UUID userId, @RequestBody UUID tradeOfferId) {
        tradeService.sell(new TradeCommand(userId, tradeOfferId));
    }
}

record TradeRequest(CryptoCurrency currency, @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount) {}


