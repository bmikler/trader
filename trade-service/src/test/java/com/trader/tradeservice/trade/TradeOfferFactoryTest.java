package com.trader.tradeservice.trade;

import com.trader.tradeservice.shared.CryptoCurrency;
import com.trader.tradeservice.rate.RateService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static java.time.Instant.ofEpochMilli;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class TradeOfferFactoryTest {

    private static final long OFFER_EXPIRATION_TIME_MINUTES = 1;
    private final Clock clock = Clock.fixed(ofEpochMilli(0), ZoneId.systemDefault());

    @Mock
    private RateService rateService;
    private TradeOfferFactory factory;

    @BeforeEach
    void init() {
        factory = new TradeOfferFactory(OFFER_EXPIRATION_TIME_MINUTES, rateService, clock);
    }

    @Test
    void createOfferShouldCreateCorrectOffer() {
        UUID userId = UUID.randomUUID();
        CryptoCurrency testCurrency = CryptoCurrency.BTC;
        BigDecimal testRate = BigDecimal.TWO;
        BigDecimal testAmount = BigDecimal.TEN;
        when(rateService.getPrice(testCurrency)).thenReturn(testRate);

        TradeOffer offer = factory.createOffer(userId, testCurrency, testAmount);

        assertThat(offer.getId()).isNotNull();
        Assertions.assertThat(offer.getCurrency()).isEqualTo(testCurrency);
        assertThat(offer.getRate()).isEqualTo(testRate);
        assertThat(offer.getAmount()).isEqualTo(testAmount);
        assertThat(offer.getExpirationDate()).isEqualTo(LocalDateTime.ofInstant(ofEpochMilli(60000), ZoneId.systemDefault()));
    }
}