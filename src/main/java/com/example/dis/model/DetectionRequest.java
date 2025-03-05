package com.example.dis.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class DetectionRequest {
    @NotBlank
    private String source;

    @Size(min = 1, max = 100)
    private List<DetectionEvent> detections;
}
