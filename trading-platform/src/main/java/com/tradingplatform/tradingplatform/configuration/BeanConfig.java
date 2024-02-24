package com.tradingplatform.tradingplatform.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class BeanConfig {

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

}
