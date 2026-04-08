package com.rydo.trip_service.dto;

import com.rydo.trip_service.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TripDetails {
    private UUID tripId;
    private UUID riderId;
    private UUID driverId;
    private TripStatus status;
}
