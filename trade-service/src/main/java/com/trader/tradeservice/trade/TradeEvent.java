package com.trader.tradeservice.trade;

import com.trader.tradeservice.shared.CryptoCurrency;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class TradeEvent extends ApplicationEvent {

    private final UUID userId;
    private final BigDecimal amount;
    private final BigDecimal rate;
    private final CryptoCurrency currency;

    public TradeEvent(Object source, UUID userId, BigDecimal amount, BigDecimal rate, CryptoCurrency currency) {
        super(source);
        this.userId = userId;
        this.amount = amount;
        this.rate = rate;
        this.currency = currency;
    }
}
