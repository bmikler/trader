package com.tradingplatform.tradingplatform.rate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;

    public List<Rate> getRateTable() {
        return rateRepository.getAll();
    }

    public Optional<BigDecimal> getPrice(CryptoCurrency currency) {
        return rateRepository.getPrice(currency);
    }
}
