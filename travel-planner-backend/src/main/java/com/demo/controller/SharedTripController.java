package com.demo.controller;

import com.demo.dto.ShareTripRequest;
import com.demo.dto.TripResponseDto;
import com.demo.model.SharedTrip;
import com.demo.model.User;
import com.demo.service.SharedTripService;
import com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shared-trips")
@CrossOrigin(origins = "http://localhost:3000")
public class SharedTripController {

    @Autowired
    private SharedTripService sharedTripService;

    @Autowired
    private UserRepository userRepository;

    // Helper to get current user
    private User getAuthenticatedUser(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Share a trip with another user by username
    @PostMapping("/share")
    public ResponseEntity<String> shareTrip(@RequestBody ShareTripRequest request,
                                            Authentication authentication) {
        User sharedBy = getAuthenticatedUser(authentication);
        sharedTripService.shareTripWithUser(request.getTripId(), request.getUsernameToShareWith(), sharedBy);
        return ResponseEntity.ok("Trip shared successfully");
    }

    // Get trips shared with authenticated user
    @GetMapping
    public ResponseEntity<List<TripResponseDto>> getSharedTrips(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        System.out.println("Fetching shared trips for user: " + user.getUsername());

        List<SharedTrip> sharedTrips = sharedTripService.getTripsSharedWithUser(user);
        System.out.println("Found " + sharedTrips.size() + " shared trips.");
        List<TripResponseDto> responseDtos = sharedTrips.stream()
                .map(sharedTrip -> {
                    var trip = sharedTrip.getTrip();
                    TripResponseDto dto = new TripResponseDto();
                    dto.setId(trip.getId());
                    dto.setTitle(trip.getTitle());
                    dto.setDestination(trip.getDestination());
                    dto.setStartDate(trip.getStartDate());
                    dto.setEndDate(trip.getEndDate());
                    dto.setNotes(trip.getNotes());
                    dto.setUsername(trip.getUser().getUsername());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/shared-by-me")
    public ResponseEntity<List<TripResponseDto>> getTripsSharedByMe(Authentication authentication) {
        User currentUser = getAuthenticatedUser(authentication);
        List<SharedTrip> sharedByMe = sharedTripService.getTripsSharedByUser(currentUser);

        List<TripResponseDto> dtos = sharedByMe.stream().map(sharedTrip -> {
            var trip = sharedTrip.getTrip();
            TripResponseDto dto = new TripResponseDto();
            dto.setId(trip.getId());
            dto.setTitle(trip.getTitle());
            dto.setDestination(trip.getDestination());
            dto.setStartDate(trip.getStartDate());
            dto.setEndDate(trip.getEndDate());
            dto.setNotes(trip.getNotes());
            // Add username/email of the user this trip is shared with
            dto.setUsername(sharedTrip.getSharedWith().getUsername());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}
