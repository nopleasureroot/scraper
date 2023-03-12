package com.service.scraper.models;


import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Target {
    private String url;
    private String state;
    private String ruleId;
    private String userId;

}
