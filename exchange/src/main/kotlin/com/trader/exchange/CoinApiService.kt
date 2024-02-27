package com.trader.exchange

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class CoinApiService(
    @Value("\${base-currency}") private val baseCurrency: String,
    @Value("\${crypto-currencies}") private val cryptoCurrencies: List<String>,
    private val webClient: WebClient
) {

    init {
        webClient.get()
            .uri("$baseCurrency?filter_asset_id=${cryptoCurrencies.joinToString(separator = ",")}")
            .retrieve()
            .bodyToMono(RatesResponse::class.java)
            .subscribe { println(it) }
    }

}
