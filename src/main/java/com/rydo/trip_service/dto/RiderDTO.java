package com.rydo.trip_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.rydo.trip_service.enums.VehicleType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDTO {
    @NotNull
    private UUID tripId;
    @NotNull
    private Double pickupLat;
    @NotNull
    private Double pickupLng;
    @NotNull
    private String pickupAddress;
    @NotNull
    private Double dropoffLat;
    @NotNull
    private Double dropoffLng;
    @NotNull
    private String dropoffAddress;
    @NotNull
    private BigDecimal estimatedFare;
    @NotNull
    private BigDecimal finalFare;
//    @NotNull
//    private BigDecimal estimatedDistanceKm;
//    @NotNull
//    private BigDecimal actualDistanceKm;





    //private Double passengerRating;      Optional: to match with high-rated drivers
}