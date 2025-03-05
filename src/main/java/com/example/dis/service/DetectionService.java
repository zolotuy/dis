package com.example.dis.service;

import com.example.dis.model.DetectionEvent;
import com.example.dis.model.DetectionRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class DetectionService {

    private static final String TOPIC_NAME = "detections-topic";

    @Autowired
    private KafkaTemplate<String, DetectionEvent> kafkaTemplate;  // Use String as key

    public ResponseEntity<String> processDetections(@Valid DetectionRequest request) {
        processDetectionsAsync(request.getSource(), request.getDetections());
        return ResponseEntity.accepted().body("Detections received and being processed.");
    }

    @Async
    public void processDetectionsAsync(String source, List<DetectionEvent> detections) {
        try {
            for (DetectionEvent detection : detections) {
                log.debug("Sending detection event to Kafka with key [{}]: {}", source, detection);
                kafkaTemplate.send(TOPIC_NAME, source, detection);
            }
            log.info("Successfully processed {} detections for source: {}", detections.size(), source);
        } catch (Exception e) {
            log.error("Error processing detections for source: {}", source, e);
        }
    }
}


