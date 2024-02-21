package com.tradingplatform.tradingplatform.rate;



import com.tradingplatform.tradingplatform.shared.CryptoCurrency;

import java.math.BigDecimal;


public record Rate(CryptoCurrency currency, BigDecimal value) {
}
