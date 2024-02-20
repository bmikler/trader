package com.tradingplatform.tradingplatform.user;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class RegisterUserEvent extends ApplicationEvent {

    private final UUID userId;

    public RegisterUserEvent(Object source, UUID userId) {
        super(source);
        this.userId = userId;
    }
}