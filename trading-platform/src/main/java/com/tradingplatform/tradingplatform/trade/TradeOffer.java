package com.tradingplatform.tradingplatform.trade;

import com.tradingplatform.tradingplatform.shared.CryptoCurrency;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@RequiredArgsConstructor
class TradeOffer {

    @Id
    private final UUID id = UUID.randomUUID();
    private final UUID userId;
    private final CryptoCurrency currency;
    private final BigDecimal rate;
    private final BigDecimal amount;
    private final LocalDateTime expirationDate;

    boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }

    boolean isForUser(UUID userId) {
        return this.userId.equals(userId);
    }
}