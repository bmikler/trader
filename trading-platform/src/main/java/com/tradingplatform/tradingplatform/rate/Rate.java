package com.tradingplatform.tradingplatform.rate;



import com.tradingplatform.tradingplatform.shared.CryptoCurrency;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record Rate(CryptoCurrency currency, BigDecimal value, LocalDateTime timestamp) {
}
