package com.tradingplatform.tradingplatform.rate;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
class DummyRateRepository implements RateRepository{

    private final Map<CryptoCurrency, BigDecimal> rates = new HashMap<>();
    private final Random random = new Random();

    @PostConstruct
    private void init() {
        for (CryptoCurrency currency : CryptoCurrency.values()) {
            rates.put(currency, BigDecimal.valueOf(random.nextDouble()));
        }
    }

    @Override
    public Optional<BigDecimal> getPrice(CryptoCurrency currency) {
        return Optional.ofNullable(rates.get(currency));
    }

    @Override
    public List<Rate> getAll() {
        return rates.keySet().stream()
                .map(currency -> new Rate(currency, rates.get(currency)))
                .toList();
    }
}
