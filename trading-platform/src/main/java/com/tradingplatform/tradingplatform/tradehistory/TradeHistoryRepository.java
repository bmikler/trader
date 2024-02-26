package com.tradingplatform.tradingplatform.tradehistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

interface TradeHistoryRepository extends JpaRepository<TradeHistoryRecord, UUID> {
    List<TradeHistoryRecord> findByUserIdAndTimestampBetween(UUID userId, LocalDateTime start, LocalDateTime end);
}
