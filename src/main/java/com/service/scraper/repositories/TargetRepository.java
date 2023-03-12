package com.service.scraper.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.scraper.entities.TargetEntity;

import jakarta.transaction.Transactional;

@Repository
public interface TargetRepository extends JpaRepository<TargetEntity, Integer> {
    TargetEntity findTargetEntitiesByUrlAndUserId(String url, String userId);

    @Transactional
    long deleteByUuid(String uuid);
}

