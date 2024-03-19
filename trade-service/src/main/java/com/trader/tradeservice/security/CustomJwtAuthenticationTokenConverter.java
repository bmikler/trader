package com.trader.tradeservice.security;

import lombok.Getter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

class CustomJwtAuthenticationTokenConverter implements Converter<Jwt, JwtAuthenticationToken> {
    @Override
    public JwtAuthenticationToken convert(Jwt source) {
        List<String> authorities = (List<String>) source.getClaims().get("authorities");
        return new CustomJwtAuthenticationToken(
                source,
                authorities.stream().map(SimpleGrantedAuthority::new).toList(),
                source.getClaim("user_id"),
                source.getClaim("user_email")
        );
    }
}

