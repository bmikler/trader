package com.tradingplatform.tradingplatform.trade;

import com.tradingplatform.tradingplatform.rate.CryptoCurrency;
import com.tradingplatform.tradingplatform.rate.RateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
class TradeOfferFactory {

    private final Long offerExpirationTimeMinutes;
    private final RateService rateService;

    TradeOfferFactory(@Value("${offer-expiration-minutes}") Long offerExpirationTimeMinutes ,RateService rateService) {
        this.rateService = rateService;
        this.offerExpirationTimeMinutes = offerExpirationTimeMinutes;
    }

    TradeOffer createOffer(UUID userId, CryptoCurrency currency, BigDecimal amount) {
        BigDecimal rate = rateService.getPrice(currency);
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(offerExpirationTimeMinutes);
        return new TradeOffer(userId, currency, rate, amount, expirationDate);
    }

}
