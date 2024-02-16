package com.tradingplatform.tradingplatform.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.util.UUID;

@Entity
@Getter
@RequiredArgsConstructor
class AppUser {
    @Id
    private final UUID id = UUID.randomUUID();
    private final String email;
    private final String password;
    @Enumerated(EnumType.STRING)
    private final UserRole role;
}
