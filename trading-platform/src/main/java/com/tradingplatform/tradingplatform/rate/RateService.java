package com.tradingplatform.tradingplatform.rate;

import com.tradingplatform.tradingplatform.shared.CryptoCurrency;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;

//    @Bean
//    NewTopic kafkaTopic() {
//        return TopicBuilder.name("rate-update").build();
//    }

    @KafkaListener(id = "trading-platform", topics = "rate-update")
    void handleKafkaMessage(String in) {
        System.err.println(in);
    }


    public List<Rate> getRateTable() {
        return rateRepository.getAll();
    }

    public BigDecimal getPrice(CryptoCurrency currency) {
        return rateRepository.getPrice(currency).orElseThrow(() -> new RuntimeException("Rate not found"));
    }
}
