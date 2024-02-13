package com.tradingplatform.tradingplatform.transaction;

import com.tradingplatform.tradingplatform.rate.CryptoCurrency;
import com.tradingplatform.tradingplatform.rate.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class TradeOfferFactory {

    private final static Long OFFER_EXPIRATION_TIME_MINUTES = 5L;
    private final RateService rateService;

    TradeOffer createOffer(UUID userId, CryptoCurrency currency, BigDecimal amount) {
        BigDecimal rate = rateService.getPrice(currency);
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(OFFER_EXPIRATION_TIME_MINUTES);
        return new TradeOffer(userId, currency, rate, amount, expirationDate);
    }

}
