package com.trader.tradingplatform.rate;



import com.trader.tradingplatform.shared.CryptoCurrency;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record Rate(CryptoCurrency currency, BigDecimal value, LocalDateTime timestamp) {
}
