package com.service.scraper.message;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.JsonNode;

public record MessageEvent(
        @NotBlank UUID uuid,
        @NotBlank JsonNode message) {
}
