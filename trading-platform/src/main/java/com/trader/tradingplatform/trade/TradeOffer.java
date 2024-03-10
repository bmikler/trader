package com.trader.tradingplatform.trade;

import com.trader.tradingplatform.shared.CryptoCurrency;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
class TradeOffer {

    @Id
    private final UUID id = UUID.randomUUID();
    private UUID userId;
    private CryptoCurrency currency;
    private BigDecimal rate;
    private BigDecimal amount;
    private LocalDateTime expirationDate;

    boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }

    boolean isForUser(UUID userId) {
        return this.userId.equals(userId);
    }
}