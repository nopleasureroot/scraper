package com.service.scraper.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.service.scraper.dto.SendJsonResponseEvent;
import com.service.scraper.kafka.propety.KafkaProperty;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {
    private final KafkaProperty kafkaProperty;

    @Bean
    public ProducerFactory<String, SendJsonResponseEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaProperty.getBootstrapServers());
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, SendJsonResponseEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}

