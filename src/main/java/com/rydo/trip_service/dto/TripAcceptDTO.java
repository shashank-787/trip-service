package com.rydo.trip_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;
@Data
public class TripAcceptDTO {
    @NotNull
    private UUID driverId;
    @NotNull
    private UUID tripId;
}
