package com.trader.tradeservice.rate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trader.tradeservice.rate.Rate;
import com.trader.tradeservice.rate.RateRepository;
import com.trader.tradeservice.shared.CryptoCurrency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
class RateMessageHandler {

    private final RateRepository rateRepository;
    private final ObjectMapper mapper;

    @KafkaListener(id = "trading-platform", topics = "rate-update")
    void handleKafkaMessage(String in) throws JsonProcessingException {
        log.info("Received message: {}", in);

        RateSnapshot rateSnapshot = mapper.readValue(in, RateSnapshot.class);

        List<Rate> rates = rateSnapshot.rates().entrySet().stream()
                .map(rate -> new Rate(rate.getKey(), rate.getValue(), rateSnapshot.timestamp()))
                .toList();

        rateRepository.updateRates(rates);
        log.info("Rates updated");
    }

    private record RateSnapshot(String id, LocalDateTime timestamp, Map<CryptoCurrency, BigDecimal> rates) { }
}
