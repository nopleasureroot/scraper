package com.service.scraper.exception;

import java.util.UUID;

public class TargetAlreadyExistException extends RuntimeException {

    public TargetAlreadyExistException(UUID uuid) {
        super(String.format("Target %s already exist", uuid));
    }
}
