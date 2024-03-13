package com.trader.tradeservice.trade;

import com.trader.tradeservice.shared.CryptoCurrency;
import com.trader.tradeservice.rate.RateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
class TradeOfferFactory {

    private final Long offerExpirationTimeMinutes;
    private final RateService rateService;
    private final Clock clock;

    TradeOfferFactory(@Value("${offer-expiration-minutes}") Long offerExpirationTimeMinutes , RateService rateService, Clock clock) {
        this.rateService = rateService;
        this.offerExpirationTimeMinutes = offerExpirationTimeMinutes;
        this.clock = clock;
    }

    TradeOffer createOffer(UUID userId, CryptoCurrency currency, BigDecimal amount) {
        BigDecimal rate = rateService.getPrice(currency);
        LocalDateTime expirationDate = LocalDateTime.now(clock).plusMinutes(offerExpirationTimeMinutes);
        return new TradeOffer(userId, currency, rate, amount, expirationDate);
    }

}
