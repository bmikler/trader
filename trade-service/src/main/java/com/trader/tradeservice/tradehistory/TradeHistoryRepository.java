package com.trader.tradeservice.tradehistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

interface TradeHistoryRepository extends JpaRepository<TradeHistoryRecord, UUID> {
    @Query("SELECT t FROM TradeHistoryRecord t WHERE t.userId = :userId AND t.timestamp BETWEEN :start AND :end")
    List<TradeHistoryRecord> findByUserIdAndTimestampBetween(UUID userId, LocalDateTime start, LocalDateTime end);
}
