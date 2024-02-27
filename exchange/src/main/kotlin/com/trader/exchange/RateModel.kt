package com.trader.exchange

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

data class RatesResponse(
    @JsonProperty("asset_id_base")
    val assetIdBase: String,
    val rates: List<RateResponseDetails>
)

data class RateResponseDetails(
    @JsonProperty("asset_id_quote")
    val assetIdQuote: String,
    val time: LocalDateTime,
    val rate: BigDecimal
)

data class RatesSnapshot(
    private val timestamp: LocalDateTime,
    private val rates: Map<String, BigDecimal>
)

fun createSnapshot(rates: List<RateResponseDetails>): RatesSnapshot =
    RatesSnapshot(rates.map { it.time }.first(), rates.associate { it.assetIdQuote to it.rate })
