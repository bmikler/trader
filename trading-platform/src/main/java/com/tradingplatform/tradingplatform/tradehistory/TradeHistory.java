package com.tradingplatform.tradingplatform.tradehistory;


import com.tradingplatform.tradingplatform.account.CryptoCurrency;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
class TradeHistory {

    private final UUID id;
    private final UUID accountId;
    private final List<TradeHistoryRecord> historyRecords;

    void addRecord(CryptoCurrency currency, BigDecimal amount, BigDecimal rate) {
        historyRecords.add(new TradeHistoryRecord(LocalDateTime.now(), currency, amount, rate));
    }

    List<TradeHistoryRecord> getHistory() {
        return Collections.unmodifiableList(historyRecords);
    }

}
