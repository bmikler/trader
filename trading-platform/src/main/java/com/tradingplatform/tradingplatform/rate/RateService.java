package com.tradingplatform.tradingplatform.rate;

import com.tradingplatform.tradingplatform.shared.CryptoCurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;

    public List<Rate> getRateTable() {
        return rateRepository.getAll();
    }

    public BigDecimal getPrice(CryptoCurrency currency) {
        return rateRepository.getPrice(currency).orElseThrow(() -> new RuntimeException("Rate not found"));
    }
}
