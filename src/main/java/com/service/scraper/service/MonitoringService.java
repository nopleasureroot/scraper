package com.service.scraper.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.service.scraper.client.LamodaClient;
import com.service.scraper.dto.SendJsonResponseEvent;
import com.service.scraper.dto.TargetState;
import com.service.scraper.entity.TargetEntity;
import com.service.scraper.repository.TargetRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class MonitoringService {
    private List<TargetEntity> activeTargets = new ArrayList<>();
    private final TargetRepository targetRepository;
    private final LamodaClient client;
    private final KafkaTemplate<String, SendJsonResponseEvent> kafkaTemplate;

    public void addTarget(TargetEntity entity) {
        activeTargets.add(entity);
        TargetThread targetThread = new TargetThread(entity);
        targetThread.start();

    }

    public void deleteTarget(TargetEntity entity) {
        targetRepository.delete(entity);
        activeTargets.stream()
                .filter(target -> target.getUuid().equals(entity.getUuid()))
                .forEach((target) -> target.setState(TargetState.STOPPED.toString()));

        activeTargets.remove(entity);
        log.info(activeTargets.toString());
    }


    @PostConstruct
    private void initTargets() {
        activeTargets = targetRepository.findAllByState(TargetState.ACTIVE.toString());

        for (TargetEntity target : activeTargets) {
            TargetThread targetThread = new TargetThread(target);
            targetThread.start();
        }
    }

    class TargetThread extends Thread {

        private final TargetEntity target;

        TargetThread(TargetEntity target) {
            this.target = target;

            init();
        }

        private void init() {
            //todo отправить пользаку что начался мониторинг
        }

        @Override
        public void run() {
            while (TargetState.ACTIVE.toString().equals(target.getState())) {

                ResponseEntity<JsonNode> productData = client.getProductData(target.getProductId());

                if (!productData.getStatusCode().equals(HttpStatusCode.valueOf(200))) {

                }

                SendJsonResponseEvent jsonResponseEvent = new SendJsonResponseEvent();
                jsonResponseEvent.setUuid(target.getUuid());
                jsonResponseEvent.setResponseBody(productData.getBody());
                sendMessage(jsonResponseEvent);
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public void sendMessage(SendJsonResponseEvent msg) {
        kafkaTemplate.send("handler-service", msg);
    }
}
