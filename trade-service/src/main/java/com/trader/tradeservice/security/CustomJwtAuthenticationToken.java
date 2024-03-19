package com.trader.tradeservice.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.UUID;

@Getter
public class CustomJwtAuthenticationToken extends JwtAuthenticationToken {
    
    private final UUID userId;
    private final String userEmail;

    public CustomJwtAuthenticationToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities, String userId, String userEmail) {
        super(jwt, authorities);
        this.userId = UUID.fromString(userId);
        this.userEmail = userEmail;
    }
}
