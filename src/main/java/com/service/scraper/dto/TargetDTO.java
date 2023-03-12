package com.service.scraper.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class TargetDTO {
    private String productId;
    private UUID ruleId;
    private UUID userId;
}
