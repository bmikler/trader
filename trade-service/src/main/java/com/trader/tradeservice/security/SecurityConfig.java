package com.trader.tradeservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
class SecurityConfig {

    @Value("${jwt-uri}")
    private String jwtUri;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
//                                .requestMatchers("/h2-console/**").permitAll() //TODO remove it
                                .requestMatchers("/api/trade/**").hasRole("REGULAR_USER")
                                .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt
                                .jwkSetUri(jwtUri)
                                .jwtAuthenticationConverter(new CustomJwtAuthenticationTokenConverter()))
                );

        return httpSecurity.build();
    }
}
