package com.tradingplatform.tradingplatform.rate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradingplatform.tradingplatform.shared.CryptoCurrency;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
