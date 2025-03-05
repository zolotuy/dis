package com.example.dis.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GeoLocation {
    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
