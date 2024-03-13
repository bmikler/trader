package com.trader.tradeservice.rate;

import com.trader.tradeservice.shared.CryptoCurrency;
import jakarta.persistence.EntityNotFoundException;
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
        return rateRepository.getPrice(currency).orElseThrow(() -> new EntityNotFoundException("Rate not found"));
    }
}
