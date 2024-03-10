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
                .route("exchange-service", r -> r.path("/exchange/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://EXCHANGE"))
                .route("trade-service", r -> r.path("/trade/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://TRADING-PLATFORM"))
                .build();
    }
}

