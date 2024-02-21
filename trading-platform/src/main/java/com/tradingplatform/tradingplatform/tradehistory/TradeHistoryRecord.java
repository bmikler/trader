package com.tradingplatform.tradingplatform.tradehistory;

import com.tradingplatform.tradingplatform.shared.CryptoCurrency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record TradeHistoryRecord(LocalDateTime dateTime, CryptoCurrency currency, BigDecimal amount, BigDecimal rate) {
}
