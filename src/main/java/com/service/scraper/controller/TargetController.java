package com.service.scraper.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.service.scraper.dto.TargetDTO;
import com.service.scraper.dto.TargetState;
import com.service.scraper.service.TargetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/targets")
@RequiredArgsConstructor
public class TargetController {

    private final TargetService targetService;

    @PostMapping(value = "/add-target",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createTarget(@RequestBody TargetDTO target) {
        targetService.create(target);
    }

    @DeleteMapping(value = "/{uuid}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTarget(@PathVariable("uuid") UUID uuid) {
        targetService.delete(uuid);
    }


    /**
     * Метод идет в базу , находит по uuid target и меняет его состояние на старт
     * TODO: надо будет дописать, чтоб внутри targetService в шедулер передавался target
     *
     * @param uuid Id таргета который нало запустить
     */
    @GetMapping(value = "/{uuid}:start",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void startTarget(@PathVariable("uuid") UUID uuid) {
        targetService.update(uuid, TargetState.ACTIVE);
    }

    /**
     * Метод идет в базу , находит по uuid target и меняет его состояние на стоп
     * TODO: надо будет дописать, чтоб внутри targetService в шедулер передавался target
     *
     * @param uuid Id таргета который нало остановить
     */
    @GetMapping(value = "/{uuid}:stop",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void stopTarget(@PathVariable("uuid") UUID uuid) {
        targetService.update(uuid, TargetState.STOPPED);
    }
}
