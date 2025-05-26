package com.demo.service;

import com.demo.dto.TripRequestDto;
import com.demo.model.Trip;
import com.demo.model.User;
import com.demo.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public List<Trip> getTripsByUser(User user) {
        return tripRepository.findByUser(user);
    }

    public Optional<Trip> getTripById(Long id) {
        return tripRepository.findById(id);
    }

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    // ✅ Add update logic here
    public Trip updateTrip(Trip existingTrip, TripRequestDto updatedDto) {
        existingTrip.setTitle(updatedDto.getTitle());
        existingTrip.setDestination(updatedDto.getDestination());
        existingTrip.setStartDate(updatedDto.getStartDate());
        existingTrip.setEndDate(updatedDto.getEndDate());
        existingTrip.setNotes(updatedDto.getNotes());
        return tripRepository.save(existingTrip);
    }
}
