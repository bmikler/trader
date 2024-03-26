package com.trader.tradeservice.trade;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
class UserCreatedMessageHandler {

    private final AccountService accountService;
    private final ObjectMapper mapper;

    @KafkaListener(id = "user", topics = "user-created")
    void handleKafkaMessage(String in) throws JsonProcessingException {
        log.info("Recieved Kaffka event {}", in);
        RegisterUserEvent registerUserEvent = mapper.readValue(in, RegisterUserEvent.class);
        accountService.createAccount(registerUserEvent);
    }

}

