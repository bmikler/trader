package com.tradingplatform.tradingplatform.tradehistory;

import com.tradingplatform.tradingplatform.shared.CryptoCurrency;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
class TradeHistoryRecord {
    @Id
    private final UUID id = UUID.randomUUID();
    private LocalDateTime timestamp;
    private UUID userId;
    private BigDecimal amount;
    private BigDecimal rate;
    private CryptoCurrency currency;

    public TradeHistoryRecord(LocalDateTime timestamp, UUID userId, BigDecimal amount, BigDecimal rate, CryptoCurrency currency) {
        this.timestamp = timestamp;
        this.userId = userId;
        this.amount = amount;
        this.rate = rate;
        this.currency = currency;
    }
}
