package com.trader.exchange

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class CoinApiWebClientConfig(@Value("\${coin-api-key}") private val apiKey: String) {

    @Bean
    fun webClient() : WebClient {
        return WebClient.builder()
            .baseUrl("https://rest.coinapi.io/v1/exchangerate/USD?filter_asset_id=BTC,ETH")
            .defaultHeader("X-CoinAPI-Key", apiKey)
            .build()
    }
}