package com.trader.tradeservice.rate;

import com.trader.tradeservice.shared.CryptoCurrency;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Primary
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
    public void updateRates(List<Rate> rates) {
        rates.forEach(rate -> this.rates.put(rate.currency(), rate.value()));
    }

    @Override
    public Optional<BigDecimal> getPrice(CryptoCurrency currency) {
        return Optional.ofNullable(rates.get(currency));
    }

    @Override
    public List<Rate> getAll() {
        return rates.keySet().stream()
                .map(currency -> new Rate(currency, rates.get(currency), LocalDateTime.now()))
                .toList();
    }
}
