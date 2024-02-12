package com.tradingplatform.tradingplatform.tradehistory;

import com.tradingplatform.tradingplatform.rate.CryptoCurrency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record TradeHistoryRecord(LocalDateTime dateTime, CryptoCurrency currency, BigDecimal amount, BigDecimal rate) {
}
