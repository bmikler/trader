package com.tradingplatform.tradingplatform.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
class AppUser {
    @Id
    private final UUID id = UUID.randomUUID();
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    AppUser(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
