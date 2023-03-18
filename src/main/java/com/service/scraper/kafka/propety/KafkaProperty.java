package com.service.scraper.kafka.propety;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;


@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProperty {
    @NotNull private String bootstrapServers;
}
