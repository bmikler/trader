package com.trader.exchange.rate

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/rates")
class RateController(private val rateService: RateService) {

    @GetMapping
    fun getLatestRates() : ResponseEntity<RatesSnapshot> {
        System.err.println("test")
        return ResponseEntity.ok(rateService.getLastRates())
    }

}