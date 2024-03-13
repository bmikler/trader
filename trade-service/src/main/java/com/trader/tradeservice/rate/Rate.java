package com.trader.tradeservice.rate;



import com.trader.tradeservice.shared.CryptoCurrency;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record Rate(CryptoCurrency currency, BigDecimal value, LocalDateTime timestamp) {
}
