package com.trader.authserver.user;

import java.util.UUID;

public record UserCreatedEvent(UUID id, String email) { }
