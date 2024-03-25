package com.trader.tradeservice.infrastructure;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class UserCreatedMessageHandler {

    private final ApplicationEventPublisher eventPublisher;

    @KafkaListener(id = "user", topics = "user-created")
    void handleKafkaMessage(String in)  {

        eventPublisher.publishEvent(new RegisterUserEvent(in));
        System.err.println("test abc");
        System.err.println(in);

    }

}

public record RegisterUserEvent(UUID id) {};
