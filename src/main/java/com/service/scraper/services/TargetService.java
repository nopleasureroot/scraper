package com.service.scraper.services;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.service.scraper.entities.TargetEntity;
import com.service.scraper.models.Target;
import com.service.scraper.repositories.TargetRepository;

import lombok.Data;

@Service
@Data
public class TargetService {
    private final TargetRepository targetRepository;

    public String create(Target target) {

        TargetEntity newTarget = new TargetEntity();
        TargetEntity oldTarget = targetRepository.findTargetEntitiesByUrlAndUserId(target.getUrl(), target.getUserId());

        if (oldTarget == null) {
            newTarget.setUuid(createUUID());
            newTarget.setUrl(target.getUrl());
            newTarget.setState(target.getState());
            newTarget.setCreated_at(getCreateTime());
            newTarget.setUpdated_at(getCreateTime());
            newTarget.setRuleId(target.getRuleId());
            newTarget.setUserId(target.getUserId());

            try {
                targetRepository.save(newTarget);
            } catch (RuntimeException e) {
                return "Something went wrong";
            }
            return "Create successful";
        } else {
            return "Target already exists";
        }
    }

    public String delete(String uuid) {
        try {
            long deletedRecords = targetRepository.deleteByUuid(uuid);
            if (deletedRecords > 0)
                return "Delete successful";
            else
                return "Delete failed";
        } catch (RuntimeException e) {
            return "Something went wrong";
        }
    }

    private static String getCreateTime() {
        LocalDateTime timedateObj;
        timedateObj = LocalDateTime.now();
        DateTimeFormatter dtf;
        dtf = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss");
        return dtf.format(timedateObj);
    }

    private static String createUUID() {
        return UUID.randomUUID().toString();
    }


}
