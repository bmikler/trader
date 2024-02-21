package com.tradingplatform.tradingplatform.tradehistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface TradeHistoryRepository extends JpaRepository<TradeHistoryRecord, UUID> {
}
