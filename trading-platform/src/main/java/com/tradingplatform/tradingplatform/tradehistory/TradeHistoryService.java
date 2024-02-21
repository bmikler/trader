package com.tradingplatform.tradingplatform.tradehistory;

import com.tradingplatform.tradingplatform.trade.TradeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

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

}
