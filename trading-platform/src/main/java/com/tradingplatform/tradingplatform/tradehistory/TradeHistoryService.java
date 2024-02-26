package com.tradingplatform.tradingplatform.tradehistory;

import com.tradingplatform.tradingplatform.shared.CryptoCurrency;
import com.tradingplatform.tradingplatform.trade.TradeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
class TradeHistoryService {

    private final TradeHistoryRepository tradeHistoryRepository;
    private final Clock clock;

    @EventListener
    public void recordTrade(TradeEvent tradeEvent) {
        TradeHistoryRecord historyRecord = new TradeHistoryRecord(LocalDateTime.now(clock), tradeEvent.getUserId(), tradeEvent.getAmount(), tradeEvent.getRate(), tradeEvent.getCurrency());
        tradeHistoryRepository.save(historyRecord);
        log.info("Transaction has been saved in history");
    }

    List<HistoryDto> getTradeHistory(HistoryQuery historyQuery) {
        return tradeHistoryRepository.findByUserIdAndTimestampBetween(historyQuery.userId(), historyQuery.start(), historyQuery.end()).stream()
                .map(record -> new HistoryDto(record.getTimestamp(), record.getAmount(), record.getRate(), record.getCurrency()))
                .toList();
    }
}

record HistoryQuery(UUID userId, LocalDateTime start, LocalDateTime end) {}

