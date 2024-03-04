package com.trader.exchange.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Configuration
class KafkaConfig(

) {
    @Bean
    fun rateUpdateTopic() : NewTopic = TopicBuilder.name("rate-update")
        .partitions(5)
        .replicas(1)
        .build()
}