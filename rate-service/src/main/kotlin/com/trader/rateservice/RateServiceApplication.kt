package com.trader.rateservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class ExchangeApplication

fun main(args: Array<String>) {
	runApplication<ExchangeApplication>(*args)
}
