package com.trader.tradeservice.trade;

import com.trader.tradeservice.security.CustomJwtAuthenticationToken;
import com.trader.tradeservice.shared.CryptoCurrency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<AccountInfoDto> getAccountInfo(CustomJwtAuthenticationToken auth) {
        return ResponseEntity.ok(accountService.getAccountInfo(auth.getUserId()));
    }

    @PostMapping
    ResponseEntity<TradeOffer> createOffer(CustomJwtAuthenticationToken auth, @Valid @RequestBody TradeOfferRequest tradeOfferRequest) {
        TradeOffer tradeOffer = tradeService.createOffer(new TradeOfferCommand(auth.getUserId(), tradeOfferRequest.currency(), tradeOfferRequest.amount()));
        return new ResponseEntity<>(tradeOffer, HttpStatus.CREATED);
    }

    @PostMapping("/buy")
    void buy(CustomJwtAuthenticationToken auth, @RequestBody TradeRequest tradeRequest) {
        tradeService.buy(new TradeCommand(auth.getUserId(), tradeRequest.tradeOfferId()));
    }

    @PostMapping("/sell")
    void sell(CustomJwtAuthenticationToken auth, @RequestBody TradeRequest tradeRequest) {
        tradeService.sell(new TradeCommand(auth.getUserId(), tradeRequest.tradeOfferId()));
    }
}

record TradeOfferRequest(CryptoCurrency currency, @DecimalMin(value = "0.000001") BigDecimal amount) {}
record TradeRequest(UUID tradeOfferId) {}


