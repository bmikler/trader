package com.trader.tradingplatform.rate;

import com.trader.tradingplatform.shared.CryptoCurrency;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
