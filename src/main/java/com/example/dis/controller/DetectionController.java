package com.example.dis.controller;

import com.example.dis.model.DetectionRequest;
import com.example.dis.service.DetectionService;
import com.example.dis.service.RateLimiterService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@Validated
public class DetectionController {

    @Autowired
    private DetectionService detectionService;

    @Autowired
    private RateLimiterService rateLimiterService;

    @PostMapping("/detections")
    public ResponseEntity<String> receiveDetections(@Valid @RequestBody DetectionRequest request) {
        String source = request.getSource();

        if (!rateLimiterService.allowRequest(source)) {
            log.warn("Rate limit exceeded for source: {}", source);
            return ResponseEntity.status(429).body("Rate limit exceeded");
        }

        log.info("Received detection request from source: {} with {} detections", source, request.getDetections().size());
        ResponseEntity<String> response = detectionService.processDetections(request);

        rateLimiterService.releaseRequest(source);
        return response;
    }
}
