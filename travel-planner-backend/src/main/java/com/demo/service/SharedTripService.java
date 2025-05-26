package com.demo.service;

import com.demo.model.SharedTrip;
import com.demo.model.Trip;
import com.demo.model.User;
import com.demo.repository.SharedTripRepository;
import com.demo.repository.TripRepository;
import com.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharedTripService {

    private final SharedTripRepository sharedTripRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public SharedTripService(SharedTripRepository sharedTripRepository, TripRepository tripRepository, UserRepository userRepository) {
        this.sharedTripRepository = sharedTripRepository;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    public SharedTrip shareTripWithUser(Long tripId, String usernameToShareWith, User sharedByUser) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        User userToShareWith = userRepository.findByUsername(usernameToShareWith)
                .orElseThrow(() -> new RuntimeException("User to share with not found"));

        // Prevent sharing with self
        if (userToShareWith.getId().equals(sharedByUser.getId())) {
            throw new RuntimeException("Cannot share trip with yourself");
        }

        // Check if already shared
        if (sharedTripRepository.findByTripAndSharedWith(trip, userToShareWith).isPresent()) {
            throw new RuntimeException("Trip already shared with this user");
        }

        SharedTrip sharedTrip = new SharedTrip(trip, userToShareWith, sharedByUser);
        return sharedTripRepository.save(sharedTrip);
    }

    public List<SharedTrip> getTripsSharedWithUser(User user) {
        return sharedTripRepository.findBySharedWith(user);
    }
    public List<SharedTrip> getTripsSharedByUser(User user) {
        return sharedTripRepository.findBySharedBy(user);
    }

}

