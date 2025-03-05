package com.example.dis.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DetectionEvent {
    @NotBlank
    private String timestamp;

    @NotBlank
    private String uniqueId;

    @NotNull
    private GeoLocation geoLocation;

    @NotBlank
    private String type;

    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private double confidence;
}
