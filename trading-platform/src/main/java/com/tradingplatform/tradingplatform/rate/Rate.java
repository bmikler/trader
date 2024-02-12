package com.tradingplatform.tradingplatform.rate;

import jakarta.persistence.Entity;

import java.math.BigDecimal;


public record Rate(CryptoCurrency currency, BigDecimal value) {
}
