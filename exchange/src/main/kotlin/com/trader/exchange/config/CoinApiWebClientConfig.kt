package com.trader.exchange.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class CoinApiWebClientConfig(
    @Value("\${coin-api-key}") private val apiKey: String,
    @Value("\${coin-api-url}") private val baseUrl: String
) {
    @Bean
    fun webClient() : WebClient {
        return WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("X-CoinAPI-Key", apiKey)
            .build()
    }
}