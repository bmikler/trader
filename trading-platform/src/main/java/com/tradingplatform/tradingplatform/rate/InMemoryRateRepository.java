package com.tradingplatform.tradingplatform.rate;

import com.tradingplatform.tradingplatform.shared.CryptoCurrency;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Primary
@Component
class InMemoryRateRepository implements RateRepository{

    private final List<Rate> rates = new ArrayList<>();

    @Override
    public void updateRates(List<Rate> rates) {
        this.rates.clear();
        this.rates.addAll(rates);
    }

    @Override
    public Optional<BigDecimal> getPrice(CryptoCurrency currency) {
        return rates.stream()
                .filter(rate -> rate.currency().equals(currency))
                .map(Rate::value)
                .findFirst();
    }

    @Override
    public List<Rate> getAll() {
        return Collections.unmodifiableList(rates);
    }

}
