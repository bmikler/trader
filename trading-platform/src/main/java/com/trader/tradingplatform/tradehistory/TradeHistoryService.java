package com.trader.tradingplatform.tradehistory;

import com.trader.tradingplatform.trade.TradeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        LocalDateTime startDate = LocalDateTime.of(historyQuery.start(), LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(historyQuery.end(), LocalTime.MAX);
        return tradeHistoryRepository.findByUserIdAndTimestampBetween(historyQuery.userId(), startDate, endDate).stream()
                .map(record -> new HistoryDto(record.getTimestamp(), record.getAmount(), record.getRate(), record.getCurrency()))
                .toList();
    }
}

record HistoryQuery(UUID userId, LocalDate start, LocalDate end) {}

