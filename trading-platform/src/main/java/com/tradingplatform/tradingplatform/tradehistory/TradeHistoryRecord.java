package com.tradingplatform.tradingplatform.tradehistory;

import com.tradingplatform.tradingplatform.account.CryptoCurrency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

record TradeHistoryRecord(LocalDateTime dateTime, CryptoCurrency currency, BigDecimal amount, BigDecimal rate) {
}
