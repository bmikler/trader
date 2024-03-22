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
                .route("rate-service", r -> r.path("/rate/**")
                        .filters(f -> f.stripPrefix(1).tokenRelay())
                        .uri("lb://RATE-SERVICE"))
                .route("trade-service", r -> r.path("/trade/**")
                        .filters(f -> f.stripPrefix(1).tokenRelay())
                        .uri("http://backend-resources:8082/api/trade"))
                .build();
    }
}

