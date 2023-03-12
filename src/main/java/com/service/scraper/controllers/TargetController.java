package com.service.scraper.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.scraper.models.Target;
import com.service.scraper.services.TargetService;

import lombok.Data;

@RestController
@RequestMapping("/targets")
@Data
public class TargetController {

    private final TargetService targetService;

    @PostMapping(value="/add-target", consumes = "application/json", produces = "application/json")
    public String createTarget(@RequestBody Target target) {
        return targetService.create(target);
    }
    @DeleteMapping(value="/{uuid}", consumes = "application/json", produces = "application/json")
    public String deleteTarget(@PathVariable("uuid") String uuid) {
        return targetService.delete(uuid);
    }

    /**
     * Метод идет в базу , находит по uuid target и меняет его состояние на старт
     * TODO: надо будет дописать, чтоб внутри targetService в шедулер передавался target
     * @param uuid Id таргета который нало запустить
     * @return строку с результатом выполнения метода
     */
    @PostMapping(value="/staer-target/{uuid}", consumes = "application/json", produces = "application/json")
    public String startTarget(@PathVariable("uuid") String uuid) {
        return "";
    }
}
