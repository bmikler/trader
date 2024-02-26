package com.tradingplatform.tradingplatform.tradehistory;

import com.tradingplatform.tradingplatform.shared.CryptoCurrency;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
class TradeHistoryRecord {
    @Id
    private final UUID id = UUID.randomUUID();
    @Getter
    private LocalDateTime timestamp;
    private UUID userId;
    @Getter
    private BigDecimal amount;
    @Getter
    private BigDecimal rate;
    @Getter
    private CryptoCurrency currency;

    public TradeHistoryRecord(LocalDateTime timestamp, UUID userId, BigDecimal amount, BigDecimal rate, CryptoCurrency currency) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.amount = amount;
        this.rate = rate;
        this.currency = currency;
    }
}
