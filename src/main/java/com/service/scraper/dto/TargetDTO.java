package com.service.scraper.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class TargetDTO {
    private UUID targetUUID;
    private String productId;
    private UUID userId;
}
