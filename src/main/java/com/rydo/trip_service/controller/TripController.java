package com.rydo.trip_service.controller;

import com.rydo.trip_service.dto.DriverDTO;
import com.rydo.trip_service.dto.RiderDTO;
import com.rydo.trip_service.dto.TripCreateRequest;
import com.rydo.trip_service.service.TripService;
import com.rydo.trip_service.entity.Trip;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<?> requestTrip(@Valid @RequestBody TripCreateRequest tripRequest) {
        Trip trip = tripService.createTrip(tripRequest);

        // Return 201 Created to rider app
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "trip_id", trip.getId(),
                "estimated_fare", trip.getEstimatedFare(),
                "surge_multiplier", trip.getSurgeMultiplier()
        ));
    }

    @PostMapping("find-riders")
    public ResponseEntity<?> getNearBy(@Valid @RequestBody DriverDTO dto){
        List<RiderDTO> ls = tripService.getNearbyRiders(dto);
        return new ResponseEntity<>(ls, HttpStatus.FOUND);
    }
}