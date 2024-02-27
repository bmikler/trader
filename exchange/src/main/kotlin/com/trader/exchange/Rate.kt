package com.trader.exchange

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime

data class RatesResponse(
    @JsonProperty("asset_id_base")
    val assetIdBase: String,
    val rates: List<RateDetails>
)

data class RateDetails(
    @JsonProperty("asset_id_quote")
    val assetIdQuote: String,
    val time: LocalDateTime,
    val rate: BigDecimal
)
