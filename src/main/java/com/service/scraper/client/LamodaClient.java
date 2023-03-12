package com.service.scraper.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

@FeignClient(name="lamodaClient", url="${clients.lamoda.base-url}")
public interface LamodaClient {

    @GetMapping
    ResponseEntity<JsonNode> getProductData(@RequestParam String sku);


}
