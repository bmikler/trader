package com.trader.exchange.rate

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDateTime


@Component
class RateService(
    @Value("\${base-currency}") private val baseCurrency: String,
    @Value("\${crypto-currencies}") private val cryptoCurrencies: List<String>,
    private val rateSnapshotRepository: RateSnapshotRepository,
    private val webClient: WebClient
) {

    private val logger = KotlinLogging.logger {}

    @Scheduled(fixedDelay = 360000, initialDelay = 10000)
    private final fun updateRates() {
        val url = "$baseCurrency?filter_asset_id=${cryptoCurrencies.joinToString(separator = ",")}"

        logger.info { "Updating rates. URL: $url" }

        webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(CoinApiResponse::class.java)
            .doOnSuccess {
                logger.info { "Rates downloaded: $it" }
                val ratesSnapshot = RatesSnapshot(it.rates)
                rateSnapshotRepository.save(ratesSnapshot)
                logger.info { "Rates saved: $ratesSnapshot" }
            }
            .doOnError {
                logger.error { "Error during rates download: $it" }
            }
            .onErrorComplete()
            .subscribe ()
    }

    fun getLastRates(): RatesSnapshot {
        return rateSnapshotRepository.findFirstByOrderByTimestampDesc() ?: throw RuntimeException("Entity not found")
    }
}
interface RateSnapshotRepository: MongoRepository<RatesSnapshot, LocalDateTime> {
    fun findFirstByOrderByTimestampDesc(): RatesSnapshot?
}
