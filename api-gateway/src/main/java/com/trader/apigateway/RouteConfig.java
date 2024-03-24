package com.trader.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RouteConfig {
    @Bean
    RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("backend-auth", r -> r.path("/backend-auth/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://BACKEND-AUTH"))
                .route("rate-service", r -> r.path("/rate-service/**")
                        .filters(f -> f.stripPrefix(1).tokenRelay())
                        .uri("lb://RATE-SERVICE"))
                .route("trade-service", r -> r.path("/trade-service/**")
                        .filters(f -> f.stripPrefix(1).tokenRelay())
                        .uri("lb://TRADE-SERVICE"))
                .build();
    }
}

