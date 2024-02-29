package com.trader.exchange

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class ExchangeApplication

fun main(args: Array<String>) {
	runApplication<ExchangeApplication>(*args)
}
