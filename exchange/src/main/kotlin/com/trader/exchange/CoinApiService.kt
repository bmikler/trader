package com.trader.exchange

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class CoinApiService(private val webClient: WebClient) {

    init {
    webClient.get()
        .retrieve()
        .bodyToMono(String::class.java)
        .subscribe { println(it) }
    }

}
