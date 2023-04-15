package com.service.scraper.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.service.scraper.dto.TargetDTO;
import com.service.scraper.dto.TargetState;
import com.service.scraper.entity.TargetEntity;
import com.service.scraper.exception.TargetAlreadyExistException;
import com.service.scraper.exception.TargetNotFoundException;
import com.service.scraper.functional.TriConsumer;
import com.service.scraper.mapper.TargetMapper;
import com.service.scraper.repository.TargetRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TargetService {
    private final TargetRepository targetRepository;
    private final MonitoringService monitoringService;
    private final TargetMapper targetMapper;
    private final TriConsumer<TargetState, TargetEntity, MonitoringService> executeToMonitoring =
            (state, target, monitoringService) -> {
                switch (state) {
                    case ACTIVE -> monitoringService.addTarget(target);
                    case STOPPED -> monitoringService.deleteTarget(target);
                }
            };

    public void create(TargetDTO targetDTO) {
//        Optional<TargetEntity> possibleTarget = targetRepository
//                .findTargetEntitiesByProductIdAndUserId(targetDTO.getProductId(), targetDTO.getUserId());
//
//        if (possibleTarget.isPresent()) {
//            throw new TargetAlreadyExistException(possibleTarget.get().getUuid());
//        }
//
//        targetRepository.save(targetEntity);
        targetRepository.findByUuid(targetDTO.getTargetUUID()).ifPresentOrElse(
                monitoringService::addTarget,
                () -> {
                    throw new TargetNotFoundException(targetDTO.getTargetUUID());
                }
        );
    }

    @Transactional
    public void delete(UUID uuid) {
        targetRepository
                .findByUuid(uuid)
                .ifPresentOrElse((monitoringService::deleteTarget),
                        () -> {
                            throw new TargetNotFoundException(uuid);
                        });

    }

    public void update(UUID uuid, TargetState state) {
        targetRepository.findByUuid(uuid)
                .map((target) -> {
                    executeToMonitoring.accept(state, target, monitoringService);
                    return targetRepository.save(targetMapper.updateEntity(target, state.toString()));
                })
                .orElseThrow(() -> {
                    throw new TargetNotFoundException(uuid);
                });
    }


}
