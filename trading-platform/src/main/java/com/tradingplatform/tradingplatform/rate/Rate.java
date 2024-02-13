package com.tradingplatform.tradingplatform.rate;



import java.math.BigDecimal;


public record Rate(CryptoCurrency currency, BigDecimal value) {
}
