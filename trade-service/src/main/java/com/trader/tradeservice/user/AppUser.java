package com.trader.tradeservice.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
