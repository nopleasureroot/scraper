package com.service.scraper.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.scraper.entity.TargetEntity;

import jakarta.transaction.Transactional;

@Repository
public interface TargetRepository extends JpaRepository<TargetEntity, UUID> {
    Optional<TargetEntity> findTargetEntitiesByProductIdAndUserId(String productId, UUID userId);

    Optional<TargetEntity> findByUuid(UUID uuid);

    List<TargetEntity> findAllByState(String state);



}

