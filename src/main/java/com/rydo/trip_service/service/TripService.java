package com.rydo.trip_service.service;

import com.rydo.trip_service.dto.DriverDTO;
import com.rydo.trip_service.dto.RiderDTO;
import com.rydo.trip_service.dto.TripCreateRequest;
import com.rydo.trip_service.entity.Trip;
import com.rydo.trip_service.enums.TripStatus;
import com.rydo.trip_service.repository.TripRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final MatchingTaskService matchingTaskService;

    public TripService(TripRepository tripRepository, MatchingTaskService matchingTaskService) {
        this.tripRepository = tripRepository;
        this.matchingTaskService = matchingTaskService;
    }
    public Trip createTrip(TripCreateRequest tripRequest) {
        // 1. Generate ULID and set initial state
        UUID uuid = UUID.randomUUID();
        Trip newTrip = convertToEntity(tripRequest);
        newTrip.setId(uuid);
        newTrip.setStatus(TripStatus.SEARCHING);

        //set estimated fare
        BigDecimal estimatedFare = tripRequest.getEstimatedDistanceKm().multiply(BigDecimal.valueOf(15));
        newTrip.setEstimatedFare(estimatedFare);
        newTrip.setFinalFare(estimatedFare.multiply(newTrip.getSurgeMultiplier()));

        //save in db
        Trip savedTrip = tripRepository.save(newTrip);
        return savedTrip;
    }

    //called by driver
    public List<RiderDTO> getNearbyRiders(DriverDTO driverDTO) {
        List<Trip> nearbyTrips = tripRepository.findNearbySearchingTrips(
                driverDTO.getDriverLat(),
                driverDTO.getDriverLon(),
                2.0,
                PageRequest.of(0, 10)
        );
        List<RiderDTO> nearbyRiders = nearbyTrips.stream()
                .map(this::mapToRiderDTO)
                .collect(Collectors.toList()); // Use .toList() if using Java 16+

        return nearbyRiders;
    }
    private RiderDTO mapToRiderDTO(Trip trip) {
        RiderDTO dto = new RiderDTO();
        dto.setTripId(trip.getId());
        dto.setPickupLat(trip.getPickupLat());
        dto.setPickupLng(trip.getPickupLng());
        return dto;
    }

    public TripCreateRequest convertToDto(Trip trip) {
        return modelMapper.map(trip, TripCreateRequest.class);
    }
    public Trip convertToEntity(TripCreateRequest tripDto) {
        return modelMapper.map(tripDto, Trip.class);
    }
}