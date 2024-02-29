package com.trader.exchange.rate

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

data class CoinApiResponse (
    @JsonProperty("asset_id_base")
    val assetIdBase: String,
    val rates: List<CoinApiResponseDetails>
)

data class CoinApiResponseDetails(
    @JsonProperty("asset_id_quote")
    val assetIdQuote: String,
    val time: LocalDateTime,
    val rate: BigDecimal
)

@Document
data class RatesSnapshot(
    @Id
    val id: String? = null,
    val timestamp: LocalDateTime,
    val rates: Map<String, BigDecimal>
) {
    constructor(rates: List<CoinApiResponseDetails>): this(null, rates.map { it.time }.first(), rates.associate { it.assetIdQuote to it.rate })
}
