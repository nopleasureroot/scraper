package com.service.scraper.exception;

import java.util.UUID;

public class TargetNotFoundException extends RuntimeException {

    public TargetNotFoundException(UUID uuid){
        super(String.format("Target: %s not found", uuid));
    }
}
