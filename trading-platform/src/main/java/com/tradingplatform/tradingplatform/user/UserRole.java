package com.tradingplatform.tradingplatform.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

enum UserRole {
    REGULAR_USER("BUY_STOCK", "SELL_STOCK", "READ_STOCK");

    private final Set<GrantedAuthority> allowedOperations;

    UserRole(String... allowedOperations) {
        this.allowedOperations = Arrays.stream(allowedOperations)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    Set<GrantedAuthority> getAllowedOperations() {
        return Collections.unmodifiableSet(allowedOperations);
    }
}

