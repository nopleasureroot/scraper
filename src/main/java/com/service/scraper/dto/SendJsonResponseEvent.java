package com.service.scraper.dto;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
public class SendJsonResponseEvent {
    UUID uuid;
    JsonNode responseBody;
}
