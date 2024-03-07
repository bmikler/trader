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
                .route("path_route", r -> r.path("/**")
                        .uri("lb://EXCHANGE"))
                .route("trading-platform", r -> r.path("/**")
                        .uri("lb://TRADING-PLATFORM"))
                .build();
    }

}

