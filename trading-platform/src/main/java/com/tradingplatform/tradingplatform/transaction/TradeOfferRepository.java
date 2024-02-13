package com.tradingplatform.tradingplatform.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface TradeOfferRepository extends JpaRepository<TradeOffer, UUID> {
}
