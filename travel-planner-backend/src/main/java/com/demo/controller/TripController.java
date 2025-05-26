package com.demo.controller;

import com.demo.dto.TripRequestDto;
import com.demo.dto.TripResponseDto;
import com.demo.model.Trip;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "http://localhost:3000")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private UserRepository userRepository;

    private User getAuthenticatedUser(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ Convert Trip to TripResponseDto
    private TripResponseDto convertToDto(Trip trip) {
        TripResponseDto dto = new TripResponseDto();
        dto.setId(trip.getId());
        dto.setTitle(trip.getTitle());
        dto.setDestination(trip.getDestination());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setNotes(trip.getNotes());
        dto.setUsername(trip.getUser().getUsername());
        return dto;
    }

    // ✅ Convert TripRequestDto to Trip
    private Trip convertToEntity(TripRequestDto requestDto, User user) {
        Trip trip = new Trip();
        trip.setTitle(requestDto.getTitle());
        trip.setDestination(requestDto.getDestination());
        trip.setStartDate(requestDto.getStartDate());
        trip.setEndDate(requestDto.getEndDate());
        trip.setNotes(requestDto.getNotes());
        trip.setUser(user); // set authenticated user
        return trip;
    }

    @PostMapping
    public ResponseEntity<TripResponseDto> createTrip(@RequestBody TripRequestDto requestDto, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Trip trip = convertToEntity(requestDto, user);
        Trip savedTrip = tripService.createTrip(trip);
        return ResponseEntity.ok(convertToDto(savedTrip));
    }

    @GetMapping
    public ResponseEntity<List<TripResponseDto>> getTrips(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        List<Trip> trips = tripService.getTripsByUser(user);
        List<TripResponseDto> responseDtos = trips.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<TripResponseDto> updateTrip(
            @PathVariable Long id,
            @RequestBody TripRequestDto requestDto,
            Authentication authentication) {

        User user = getAuthenticatedUser(authentication);
        
        Trip existingTrip = tripService.getTripById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        // ✅ Check if the trip belongs to the authenticated user
        if (!existingTrip.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        Trip updatedTrip = tripService.updateTrip(existingTrip, requestDto);
        return ResponseEntity.ok(convertToDto(updatedTrip));
    }

}
